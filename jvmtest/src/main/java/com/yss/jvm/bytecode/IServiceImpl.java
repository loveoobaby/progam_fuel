package com.yss.jvm.bytecode;

public class IServiceImpl implements IService {
    @Override
    public void request() {
        System.out.println("request");
    }
}
