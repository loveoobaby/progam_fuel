package com.yss.asm.demo.staticfield;

public class FieldTest {

    private long time = 0;

    public void sayHello(){
        time = System.currentTimeMillis();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        time = System.currentTimeMillis() - time;
    }

}
