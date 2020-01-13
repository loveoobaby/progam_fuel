package com.yss.jvm.bytecode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxy implements InvocationHandler {

    private IService realSubject;

    public DynamicProxy(IService service){
        this.realSubject = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");
        Object ret = method.invoke(realSubject, args);
        System.out.println("after invoke");
        return ret;
    }
}
