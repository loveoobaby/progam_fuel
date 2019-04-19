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





























