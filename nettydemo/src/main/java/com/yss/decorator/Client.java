package com.yss.decorator;

public class Client {
    public static void main(String[] args) {
        Component component = new Decorator2(new Decorator1(new ConcreteComponent()));
        component.doSomething();
    }
}
