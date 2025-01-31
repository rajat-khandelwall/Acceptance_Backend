package vw.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConDemo {
    public static void main(String[] args) {
        List<String> al = new CopyOnWriteArrayList<>();
        al.add("A");
        al.add("B");
        al.add("C");
        al.add("D");
        al.add("E");
        Iterator<String> iterator = al.iterator();
        while (iterator.hasNext()){
            String str = iterator.next();
            System.out.println(str);
            al.add("F");
        }
    }
}
