package ch5;

import ch4.Dish;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
        List<Integer> dishNameLength = menu.stream().map(Dish::getName).map(String::length).collect(Collectors.toList());
//        for (Integer integer : dishNameLength) {
//            System.out.println(integer);
//        }

        List<String> words = Arrays.asList("hello", "world");
        // flatMap()将生成的各个单个流合并并扁平化为单个流
        List<String> uniqueWords = words.stream().map(word -> word.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        for (String str : uniqueWords) {
            System.out.println(str);
        }

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream().map(n -> n * n).collect(Collectors.toList());

        List<Integer> number1 = Arrays.asList(1, 2, 3);
        List<Integer> number2 = Arrays.asList(4, 5);
        List<int[]> pairs1 = number1.stream().flatMap(i -> number2.stream().map(j -> new int[]{i, j})).collect(Collectors.toList());
        List<int[]> pairs2 = number1.stream().flatMap(i -> number2.stream().filter(j -> (i + j) % 3 == 0).map(j -> new int[]{i, j})).collect(Collectors.toList());

        // 查找和匹配（allMatch, anyMatch, noneMatch, findFirst, findAny）
        if (menu.stream().anyMatch(dish -> dish.isVegetarian())) {
            System.out.println(" some thing");
        }
        boolean isHealthy = menu.stream().allMatch(dish -> dish.getCalories() < 1000);

        //findAny()返回当前流中的任意元素
        Optional<Dish> dish = menu.stream().filter(Dish::isVegetarian).findAny();

        //reduce()
        Optional<Integer> max = numbers.stream().reduce(Integer::max);

        int count = menu.stream().map(dish1 -> 1).reduce(0, (a, b) -> a+b);
    }
}
