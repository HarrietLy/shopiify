package com.harriet.shopiify.learning;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class stream {

    public static void main (String[] args){
//        chunkingAListToSmallerLists(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
//        flattenListOfListToList();
        System.out.println("streamDemo: " + streamDemo() +" milliseconds");
        System.out.println("===================");
        System.out.println("loopEquivalent: "+loopEquivalent()+" milliseconds");
      }
    private static long loopEquivalent(){
        List<Person> mockData = getMockData();
        // build a hashmap where key is the list of fruits sorted, then iterate through the key-value pairs to build the result string
        long startTime = System.currentTimeMillis();
        Map<List<Fruit>, List<Person>> favFruits = new HashMap<>();
        for (Person person : mockData){
            person.getFavoriteFruits().sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
            List<Person> newValue= favFruits.getOrDefault(person.getFavoriteFruits(),new ArrayList<>());
            newValue.add(person);
            favFruits.put(person.getFavoriteFruits(), newValue);
        }
        List<String> res = new ArrayList<>();
        for (Map.Entry<List<Fruit>, List<Person>> pair : favFruits.entrySet()){
            double totalPrice = 0;
            for (Fruit fruit: pair.getKey()){
                totalPrice+=fruit.getPrice();
            }
            List<Fruit> fruits = pair.getKey();
            List<String> fruitsStr = fruits.stream().map(Fruit::getName).collect(toList());
            List<Person> persons = pair.getValue();
            for (Person person : persons){
                res.add(person.getName() + ", together with "+(persons.size()-1)+" other people likes "+ StringUtils.collectionToCommaDelimitedString(fruitsStr)+" with average price "+totalPrice/fruits.size());
            }
        }
        long endTime = System.currentTimeMillis();
        res.forEach(System.out::println);
        return endTime-startTime;
    }

    private static long streamDemo(){
        //transform the list of persons to a hashmap of list of persons grouped by their fav fruits
        List<Person> mockData = getMockData();
        long startTime = System.currentTimeMillis();
        List<String> res = mockData.stream().collect(Collectors.groupingBy(person ->
                {
                    Collections.sort(person.getFavoriteFruits(), (o1, o2) -> o1.getName().compareTo(o2.getName()));
                    return person.getFavoriteFruits();
                }
        )).entrySet().stream().flatMap(
                pair -> {
                    List<String> fruits = pair.getKey().stream().map(Fruit::getName).collect(toList());
                    double avgPrice = pair.getKey().stream().mapToDouble(Fruit::getPrice).average().orElse(0.0);
                    List<Person> persons = pair.getValue();
                    return persons.stream().map(person -> person.getName() + ", together with " + (persons.size() - 1) +
                            " other people likes " + StringUtils.collectionToCommaDelimitedString(fruits) + " with average price " + avgPrice);
                }
        ).toList();
        long endTime = System.currentTimeMillis();
        res.forEach(System.out::println);
        return endTime-startTime;
    }

    private static List<Person> getMockData (){
        List<Person> mockData = new ArrayList<>();
        Fruit apple = new Fruit("apple",2.5f);
        Fruit orange = new Fruit("orange",3.6f);
        Fruit mango = new Fruit("mango",4.6f);
        Fruit avocado = new Fruit("avocado",6.0f);
        mockData.add(new Person("personA", 23, Arrays.asList(apple)));
        mockData.add(new Person("personB", 33, Arrays.asList(mango,orange)));
        mockData.add(new Person("personC", 33, Arrays.asList(apple,orange)));
        mockData.add(new Person("personD", 66, Arrays.asList(orange,mango)));
        mockData.add(new Person("personE", 43, Arrays.asList(orange)));
        mockData.add(new Person("personF", 53, Arrays.asList(apple)));
        mockData.add(new Person("personG", 23, Arrays.asList(mango, avocado)));
        mockData.add(new Person("personH", 33,Arrays.asList(avocado, mango)));
        mockData.add(new Person("personI", 33,Arrays.asList(avocado, mango)));
        return mockData;
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
        List<String> res = testList.stream().collect(Collectors.groupingBy(num -> counter.getAndIncrement() / chunkSize)).entrySet().stream()
                .map(entry -> {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue());
                    return entry.getKey() + ": " + StringUtils.collectionToCommaDelimitedString(entry.getValue());
                }).toList();

        res.forEach(System.out::println);
    }



    //flatmap
    private static void flattenListOfListToList(){
        List<List<Integer>> input = Arrays.asList(Arrays.asList(1,2,3),Arrays.asList(4,5,313,6),Arrays.asList(7,8,9,10,11));
        List<String> res = input.stream().flatMap(list->
                list.stream().map(e->(e+1)+" test")).collect(toList());
        res.forEach(System.out::println);
    }
}
