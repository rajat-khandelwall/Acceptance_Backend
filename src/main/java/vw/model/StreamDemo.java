package vw.model;

import org.apache.commons.collections4.ArrayStack;
import org.apache.commons.collections4.MapUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> al = new ArrayList<>();
        for(int i=1;i<=20;i++){
            al.add(i);
        }
        al.stream().filter(i -> i % 2 == 0).collect(Collectors.toList()).forEach(System.out::println);

        List<String> list = new ArrayList<>();
        list.add("mahesh");
        list.add("ganesh");
        list.add("saurav");
        list.add("seeta");
        list.stream().map(s->s.toUpperCase()).collect(Collectors.toList()).forEach(System.out::println);

        list.stream().map(s->s.length()).collect(Collectors.toList()).forEach(System.out::println);


    }
}
