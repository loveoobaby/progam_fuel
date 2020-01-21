package com.yss.decorator;

public class Decorator1 implements Component {

    private Component component;

    public Decorator1(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething() {
        System.out.println("Decorator1 增强");
        component.doSomething();
    }
}
