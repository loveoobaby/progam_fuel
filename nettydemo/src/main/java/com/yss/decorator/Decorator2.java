package com.yss.decorator;

public class Decorator2 implements Component {
    Component component;

    public Decorator2(Component component){
        this.component = component;
    }

    @Override
    public void doSomething() {
        System.out.println("Decorator2 增强");
        component.doSomething();
    }
}
