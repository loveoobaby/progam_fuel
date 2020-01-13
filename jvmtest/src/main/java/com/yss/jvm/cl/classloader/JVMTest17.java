package com.yss.jvm.cl.classloader;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ServiceLoader;

public class JVMTest17 {


    public static void main(String[] args) throws SQLException {
//        ServiceLoader<Driver> loader = ServiceLoader.load(Driver.class);
//        Iterator<Driver> iterator = loader.iterator();
//
//        while (iterator.hasNext()){
//            Driver driver = iterator.next();
//            System.out.println("driver = " +  driver.getClass() + " , classloader =  " + driver.getClass().getClassLoader());
//        }
//
//        System.out.println("ServiceLoader class loader = " + ServiceLoader.class.getClassLoader());
//        System.out.println("current context class loader = " + Thread.currentThread().getContextClassLoader());

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "12345678");
    }
}
