package com.yss.jvm.classloader;

import java.io.*;

/**
 *  1. 每个类加载器都有自己的命名空间，命名空间由该类加载器及所有父类加载器的类组成
 *  2. 在同一命名空间下，不会出现类的完整名字相同的两个类
 *  3. 在不同的命名空间下，有可能出现类的完整名字相同的两个类
 *  4. 子加载器加载的类可以看到父类加载器加载的类；父加载器加载的类却看不到子加载器加载的类
 *
 *  类的卸载：
 *    1. 由java虚拟机自带的类加载器加载的类，在虚拟机的生命周期中，始终不会被
 *       卸载。前面已经介绍过，java虚拟机自带的类加载器包括根类加载器、扩展类加载器
 *       和系统类加载器。java虚拟机本身始终会引用这些类加载器，而这些类加载器则会始终
 *       引用它们所加载的类的Class对象，因此这个Class对象始终是可触及的。
 *    2. 由用户自定义的类加载器所加载的类是可以被卸载的
 *
 *  在类加载器的内部实现中，用一个Java集合来存放所加载的类的引用。另一方面，一个Class对象总是会引用它的类加载器，
 *  调用Class对象的getClassLoader方法，就能获得它的类加载器。由此可见，Class实例与加载器之间是双向关联关系。
 */
public class MyClassLoader extends ClassLoader {

    private final String classLoaderName;
    private final String fileExtension = ".class";
    private String classPath;

    public MyClassLoader(String classLoaderName){
        super();
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader(String classLoaderName, ClassLoader parent){
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    public void setClasspath(String classpath){
        this.classPath = classpath;
    }



    public Class findClass(String name) {
        byte[] b = loadClassData(name);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassData(String name) {
        // load the class data from the connection
//        String fileName = name.replace(".", "/") + this.fileExtension;
        System.out.println("loadClassData: " + name);
        String fileName = this.classPath + name.replace(".", "/") + this.fileExtension;
        byte[] data = null;
        try(FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedInputStream reader = new BufferedInputStream(fileInputStream);
            ByteArrayOutputStream out = new ByteArrayOutputStream()){

            byte[] temp = new byte[1024];
            int len = 0;
            while ((len = reader.read(temp, 0, 1024))!= -1){
                out.write(temp, 0, len);
            }
            data = out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        {
            ClassLoader classLoader = new MyClassLoader("test");
            Class<?> clazz = classLoader.loadClass("com.yss.jvm.classloader.TestClassLoader");
            Object o = clazz.newInstance();
            System.out.println(o);

            ClassLoader loader2 = new MyClassLoader("test2");
            Class<?> clazz2 = loader2.loadClass("com.yss.jvm.classloader.TestClassLoader");
            System.out.println(clazz2.hashCode());
            System.out.println(clazz.hashCode());
        }
        System.out.println("----------------");
        {
            ClassLoader classLoader = new MyClassLoader("test");
            Class<?> clazz = classLoader.loadClass("com.yss.jvm.classloader.TestClassLoader");
            Object o = clazz.newInstance();
            System.out.println(o);

            ClassLoader loader2 = new MyClassLoader( "test2", classLoader);
            Class<?> clazz2 = loader2.loadClass("com.yss.jvm.classloader.TestClassLoader");
            System.out.println(clazz2.hashCode());
            System.out.println(clazz.hashCode());
        }


    }
}
