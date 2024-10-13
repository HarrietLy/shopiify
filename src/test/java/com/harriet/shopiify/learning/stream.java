package com.harriet.shopiify.learning;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class stream {

    public static void main (String[] args){

//        chunkingAListToSmallerLists(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
//        flattenListOfListToList();
        List<Person> mockData = new ArrayList<>();
        Fruit apple = new Fruit("apple",2.5f);
        Fruit orange = new Fruit("orange",3.6f);
        Fruit mango = new Fruit("mango",4.6f);
        Fruit avocado = new Fruit("avocado",6.0f);
        mockData.add(new Person("personA", 23, Arrays.asList(apple)));
        mockData.add(new Person("personB", 33, Arrays.asList(mango,orange)));
        mockData.add(new Person("personC", 33, Arrays.asList(apple,orange)));
        mockData.add(new Person("personD", 66, Arrays.asList(mango,orange)));
        mockData.add(new Person("personE", 43, Arrays.asList(orange)));
        mockData.add(new Person("personF", 53, Arrays.asList(apple)));
        mockData.add(new Person("personG", 23, Arrays.asList(avocado, mango)));
        mockData.add(new Person("personH", 33, Arrays.asList(avocado, mango)));
        //transform the list of person to list of list of persons group by their fav fruits
        List<String> res = mockData.stream().collect(Collectors.groupingBy(person->person.getFavoriteFruits())).entrySet().stream().flatMap(
                pair->{
                    Stream<String> mappedPair;
                    List<String> fruits = pair.getKey().stream().map(frui->frui.getName()).collect(Collectors.toList());
                    Double avgPrice = pair.getKey().stream().mapToDouble(frui->frui.getPrice()).average().orElse(Double.NaN);
                    List<Person> persons = pair.getValue();
                    mappedPair = persons.stream().map(person->person.getName()  + ", together with "+(persons.size()-1)+" other people likes "+ StringUtils.collectionToCommaDelimitedString(fruits)+" with average price "+avgPrice);
                    return mappedPair;
                }
        ).collect(Collectors.toList());
        res.stream().forEach(System.out::println);

    }


    //get the groups of persons, that are age more than 30, buy their list of favourite fruits
    // list of persons, ['apple']:[personA, personB, personC], ['apple','orange']:[personD, personG]
    @Data
    @AllArgsConstructor
    private static class Person {
        private String name;
        private int age;
        private List<Fruit> favoriteFruits;
    }

    @Data
    @AllArgsConstructor
    private static class Fruit{
        private String name;
        private Float price;
    }

    private static void chunkingAListToSmallerListsUsingGroupBy(List<Integer> testList){
        int chunkSize = 3;
        AtomicInteger counter = new AtomicInteger();
        // transform the list into list of chunks of 2
        List<String> res = testList.stream().collect(Collectors.groupingBy(num -> counter.getAndIncrement()/chunkSize)).entrySet().stream()
                .map(entry -> {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue());
                    return entry.getKey() +": "+ StringUtils.collectionToCommaDelimitedString(entry.getValue());
                }).collect(Collectors.toList());

        res.stream().forEach(System.out::println);
    }



    //flatmap
    private static void flattenListOfListToList(){
        List<List<Integer>> input = Arrays.asList(Arrays.asList(1,2,3),Arrays.asList(4,5,313,6),Arrays.asList(7,8,9,10,11));
        List<String> res = input.stream().flatMap(list->
                list.stream().map(e->(e+1)+" test")).collect(Collectors.toList());
        res.stream().forEach(System.out::println);
    }
}
