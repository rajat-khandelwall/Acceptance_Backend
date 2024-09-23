package vw.model;

import java.util.Collection;
import java.util.Collections;

public class ThreadDemo {
    public static void main(String[] args) {
        Runnable r = ()->{
            Thread.currentThread().setName("Child Thread");
            System.out.println("From Runnable "+Thread.currentThread().getName());
            for (int i=0;i<10;i++){
                System.out.println(i);
            }
        };

        System.out.println("From Main "+Thread.currentThread().getName());
        Thread t = new Thread(r);
        t.start();
        for(int i=10;i<20;i++){
            System.out.println(i);
        }
    }



}
