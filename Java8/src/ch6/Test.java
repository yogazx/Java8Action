package ch6;

import ch4.Dish;

import java.security.KeyStore;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

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

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(dishCaloriesComparator));

        // 汇总操作Collectors.summingInt Collectors.summingLong Collectors.summingDouble
        int total = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        // 这种也是求和 menu.stream().mapToInt(Dish::getCalories).sum();性能最佳，使用mapToInt避免自动拆箱操作
        // 这种也是求和 menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();

        //使用reducing()
        int total2 = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (d1, d2) -> d1 + d2));
        //System.out.println(total2);

        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5).stream();
        List<Integer> numbers = stream.reduce(new ArrayList<Integer>(),
                (List<Integer> list, Integer e) -> {
                    list.add(e);
                    return list;
                },
                (List<Integer> list1, List<Integer> list2) -> {
                    list1.addAll(list2);
                    return list1;
                });

        // 下面三种写法等价
        String name1 = menu.stream().map(Dish::getName).collect(Collectors.joining());
        String name2 = menu.stream().collect(Collectors.reducing("", Dish::getName, (dish1, dish2) -> dish1 + dish2));
        String name3 = menu.stream().map(Dish::getName).reduce("", (dish1, dish2) -> dish1 + dish2);

        // 分组groupingBy()，默认会使用toList()，所以通常toList()会省略
        Map<Dish.Type, List<Dish>> map = menu.stream().collect(Collectors.groupingBy(Dish::getType, toList()));
        // 多级分组 groupingBy传groupingBy
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        groupingBy((Dish dish) -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }, toList())));
        // lambda遍历map
        dishesByTypeCaloricLevel.entrySet().iterator().forEachRemaining(item -> item.getValue().entrySet().iterator().forEachRemaining(item2 -> System.out.println(item2.getValue())));
        dishesByTypeCaloricLevel.forEach((K, V) -> V.forEach((subK, subV) -> System.out.println(K + " : " + subV)));
        System.out.println();


        // 按子组收集数据 子收集器可以是任意类型
        Map<Dish.Type, Long> typesCount = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        maxBy(Comparator.comparingInt(Dish::getCalories))));

        // 使用Collectors.collectingAndThen去除结果值上包裹的Optional
        Map<Dish.Type, Dish> mostCaloricByType2 = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));

        //
        Map<Dish.Type, Integer> totalCaloriesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));

        // 常与groupingBy联合使用的mapping()
        // mapping接受两个参数，一个函数对流中的元素做变换，另一个则将变换的结果对象收集起来
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.mapping((Dish dish) -> {
                            if (dish.getCalories() <= 401) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }, toCollection(HashSet::new))));

        caloricLevelsByType.entrySet().stream().forEach(System.out::println);

        // 分区 partitioning
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishByType = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
    }


    public enum CaloricLevel {DIET, NORMAL, FAT}
}
