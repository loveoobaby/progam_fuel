package com.yss.jvm.bytecode;

import java.lang.reflect.Proxy;

public class PrxoyMain {

    public static void main(String[] args) {
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        IService service = new IServiceImpl();
        DynamicProxy dynamicProxy = new DynamicProxy(service);
        Class<?> cl = service.getClass();

        IService proxy = (IService) Proxy.newProxyInstance(cl.getClassLoader(), new Class[]{IService.class}, dynamicProxy);
        proxy.request();
        System.out.println(proxy.hashCode());
        System.out.println(proxy.toString());
    }


}
