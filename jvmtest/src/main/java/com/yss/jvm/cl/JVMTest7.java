package com.yss.jvm;

/**
 * JVM规范允许类加载器在预料到某个类将要
 * 被使用时就预先加载它，如果在预先加载过程中遇到.class文件缺失或存在错误，
 * 类加载器必须在程序首次主动使用该类时才报告错误（LinkageError）
 *
 * 如果该类一直没有被使用，那么类加载器不需要报告错误
 *
 * -----------------------------------------------
 * 当java虚拟机初始化一个类的时候，要求它的所有父类必须都被初始化，但是这条规则并不适于接口。
 *   1. 在初始化一个类的时候，并不会先初始化它所实现的接口
 *   2. 在初始化一个接口时，并不会先初始化它的父接口
 *   因此，一个父接口并不会因为它的子接口或者实现类的初始化而初始化。只有当程序首次主动使用特定接口的静态变量时，
 *   才会导致该接口的初始化
 * -----------------------------------------------
 *
 * 类的初始化步骤：
 *   假如这个类还没有被加载和连接，那就先进行加载和连接
 *   假如类存在直接的父类，并且这个父类还没有被初始化，那就先初始化直接父类
 *   假如类中存在初始化语句，那就依次执行这些初始化语句
 *
 * 类的初始化时机：
 *   1. 创建类的实例
 *   2. 访问某个类或接口的静态变量，或者对静态变量赋值
 *   3. 调用类的静态方法
 *   4. 反射，Class.forName("com.test.Test")
 *   5. 初始化一个类的子类
 *   6. JVM启动时被标明的启动类
 *
 * 调用ClassLoader类的loadClass方法加载一个类，并不是对类的主动使用，不会导致类的初始化
 *
 * 在双亲委托机制中，除了java虚拟机自带的BootStrap加载器以外，其余的类加载器都有且只有一个父类加载器。
 * 当java程序请求加载器loader1加载Sample类时，loader1首先委托自己的父类加载器去加载Sample类，若父类加载器能
 * 加载到，则由父类加载器完成加载任务，否则才由加载器loader1本省加载Sample类
 *
 * java虚拟机自带了以下几种加载器：
 *   1. BootStrap类加载器：该加载器没有父类加载器。它负责加载虚拟机的核心类库，如java.lang.*等。根类加载器从系统属性
 *      sun.boot.class.path所指定的目录中加载类。根类加载器的实现依赖于底层操作系统，属于虚拟机的实现的一部分，它并没有
 *      继承java.lang.ClassLoader
 *   2. 扩展类加载器：它的父类加载器为根类加载器。他从java.ext.dirs系统属性所指的目录中加载类库，或者从JDK的安装目录的
 *      jre/lib/ext子目录下加载类库，如果用户创建的jar放在这个目录下也会自动由扩展类加载器加载。扩展类加载器是存java类，
 *      是java.lang.ClassLoader的子类
 *   3. 系统类加载器：也称应用类加载器，它的父加载器为扩展类加载器，它从环境变量classpath或者系统属性java.class.path所指
 *      目录中加载类，它是用户自定义加载器的默认父类加载器。系统类加载器是存java类，是java.lang.ClassLoader的子类。
 *
 */

public class JVMTest7 {
    public static void main(String[] args) {
        System.out.println(Child7.str);
//        System.out.println(Child7.c);
    }
}

class Parent7 {
    public static String str = "hello";

    static {
        System.out.println("Parent7 static code");
    }
}

class Child7 extends Parent7 {
    public static String c = " child";

    static {
        System.out.println("Child7 static code");
    }
}
