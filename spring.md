# 1. Servlet容器如何启动Spring容器(War包方式部署)

## 1.1  ServletContainerInitializer初始化器
+ servlet规范中通过配置一个ServletContainerInitializer实现类，实现在Web容器启动时可以给第三方组件做一些初始化的工作。

+ spring-web的jar包下META-INF/services目录下，有一个名为javax.servlet.ServletContainerInitializer的文件。这个文件可以配置一个实现ServletContainerInitializer接口的实现类。在这个实现类中初始化spring IOC容器。在spring中该接口实现类是org.springframework.web.SpringServletContainerInitializer
+ 这种方式是java采用的一种机制，称为Service Provider，service目录下的文件称为provider configuration file，目的就是让提供者实现具体的实现类。

```java
public interface ServletContainerInitializer {
    void onStartup(Set<Class<?>> var1, ServletContext var2) throws ServletException;
}
```

+ 这个接口实现类就是servlet容器与spring web框架的连接点。程序在执行时就可以从Tomcat等容器进入到Spring的代码上来。



# 2.Spring Web容器的启动过程(War包方式部署)

```java
// Servelt容器会收集WebApplicationInitializer的实现类及其子类，将其作为参数传入onStartup
@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {
    	@Override
	public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext)
			throws ServletException {

		List<WebApplicationInitializer> initializers = new LinkedList<WebApplicationInitializer>();

		if (webAppInitializerClasses != null) {
			for (Class<?> waiClass : webAppInitializerClasses) {
                // 过滤接口、抽象类
				if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) &&
						WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
					try {
						initializers.add((WebApplicationInitializer) waiClass.newInstance());
					}
					catch (Throwable ex) {
						throw new ServletException("Failed to instantiate WebApplicationInitializer class", ex);
					}
				}
			}
		}

		if (initializers.isEmpty()) {
			servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
			return;
		}

		servletContext.log(initializers.size() + " Spring WebApplicationInitializers detected on classpath");
		AnnotationAwareOrderComparator.sort(initializers);
        // 调用initializer进行spring web容器初始化操作
		for (WebApplicationInitializer initializer : initializers) {
			initializer.onStartup(servletContext);
		}
	}

}
```



# 3. Spring的定时器有三种模式

### 1. fixedDelay

`@Scheduled(fixedDelay = 3 * 1000)`:   间隔3秒后,执行下一个任务

### 2.cron

`@Scheduled(cron = "0/5 * * * * ? ")`: 可以理解为5s就是一个周期。 但是是单线程执行，如果一个任务在5秒内没有运行完，下一个任务将不再调度，只有等到任务运行完成，并且当前时间是5的倍数才会执行

### 3. fixedRate

`@Scheduled(fixedRate = 5 * 1000)` : 每隔5秒运行一次，但如果任务运行时间超过5秒，下一个任务会立即执行



# 4. Transactional

```java
@Service
public class OrderService {
   
    // 如果当前存在事务则使用该事务，如果不存在则新建一个事务
    @Transactional
    public void insertOrder() {
        //insert log info
        //insertOrder
        //updateAccount
    }
}
```



# 5. 常用注解

## 5.1项目配置类注解

+ **@SpringBootApplication 注解**：这是一个复合注解，包含了**@SpringBootConfiguration，@EnableAutoConfiguration，@ComponentScan**这三个注解。

+ **@SpringBootConfiguration**：标注当前类是配置类，这个注解继承自**@Configuration**，并会将当前类内声明一个或多个以@bean标注的方法实例纳入到spring容器中，并且实例名就是方法名。

+ **@EnableAutoConfiguration**：自动装配注解，这个注解会根据添加的组件jar来完成一系列默认配置。

+ **@ComponentScan**:扫描当前包及其子包下被@Component，@Controller，@Service，@Repository注解标记的类并纳入到spring容器中进行管理

+ **@ServletComponentScan**： Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册。这个注解定义包扫描的范围。

+ **@MapperScan**: spring-boot支持mybatis组件的一个注解，通过此注解指定mybatis接口类的路径，即可完成对mybatis接口的扫描。当然需要添加响应的依赖：

  ```XML
  <dependency>
     <groupId>org.mybatis.spring.boot</groupId>
     <artifactId>mybatis-spring-boot-starter</artifactId>
     <version>RELEASE</version>
  </dependency>
  ```

## 5.2 资源导入注解

+ **@ImportResource(locations={}) **导入其他xml配置文件，需要标准在主配置类上。导入property的配置文件 **@PropertySource**指定文件路径，这个相当于使用spring的**<importresource/>**标签来完成配置项的引入。

+ **@import**注解是一个可以将普通类导入到spring容器中做管理

## 5.3 Controller层注解

+ **@Controller **表明这个类是一个控制器类，和**@RequestMapping**来配合使用拦截请求
+ **@RestController** 是@Controller 和@ResponseBody的结合，一个类被加上@RestController 注解，数据接口中就不再需要添加@ResponseBody
+ **@CrossOrigin:@CrossOrigin(origins = "", maxAge = 1000) **这个注解主要是为了解决跨域访问的问题。这个注解可以为整个controller配置启用跨域，也可以在方法级别启用。

## 5.4  Servcie层注解

+ **@Service：**这个注解用来标记业务层的组件，我们会将业务逻辑处理的类都会加上这个注解交给spring容器。事务的切面也会配置在这一层。

## 5.5 持久层注解

+ **@Repository**：@Repository注解类作为DAO对象，管理操作数据库的对象。总得来看，@Component, @Service, @Controller, @Repository是spring注解，注解后可以被spring框架所扫描并注入到spring容器来进行管理。@Component是通用注解，其他三个注解是这个注解的拓展，并且具有了特定的功能。











































