package com.yss.decorator;

public class ConcreteComponent implements Component{
    @Override
    public void doSomething() {
        System.out.println("实现了xx功能");
    }
}
