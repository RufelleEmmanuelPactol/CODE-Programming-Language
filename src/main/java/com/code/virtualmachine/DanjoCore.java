package com.code.virtualmachine;

public class DanjoCore {


    public DanjoCore(){

    }


    public void call(){
        SimpleTimer timer = new SimpleTimer();
        timer.startTime();
        System.out.println("DanjoCore called");
        System.out.println("Calling took " + timer.endTime());
    }
}
