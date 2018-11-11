package ch5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DESC chapter5 working with stream
 *
 * @author You Jia Ge
 * Created Time 2018/11/11
 */
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

        // 用谓词筛选
        List<Dish> vegetarianMenu = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());

        // 筛选各异的元素 distinct()
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        // 截短流
        List<Dish> dishes = menu.stream()
                .filter(dish -> dish.getCalories() > 400)
                .limit(3)
                .collect(Collectors.toList());

        // 跳过元素
        List<Dish> dishList = menu.stream()
                .filter(dish -> dish.getCalories() > 400)
                .skip(1)
                .collect(Collectors.toList());


    }
}
