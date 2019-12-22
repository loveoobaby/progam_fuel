package com.yss.jvm.classloader;

/**
 * JVM自带了三种加载器，他们的类加载路径是是不同的
 *    1. 根类加载器查找路径是${JRE_HOME}/lib/rt.jar或者-Xbootclasspath选项指定
 *    2. 扩展类加载器加载路径是${JRE_HOME}/lib/ext/*.jar或者-Djava.ext.dirs指定的目录
 *    3. 系统类加载器加载CLASSPATH环境变量下的类
 *
 *
 */
public class ClassLoaderPathTest {
}
