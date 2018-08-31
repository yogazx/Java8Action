package java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DESC
 *
 * @author You Jia Ge
 * Created Time 2018/6/7
 */
public class FilterApple {

    /**
     * 此注解代表可以对该接口方法采用lambda表达式(只适用于接口中只含有一个方法)
     */
    //@FunctionalInterface
    public interface AppleFilter {
        boolean filter(Apple apple);
        //int check(Apple apple);
    }

    public static List<Apple> findApple(List<Apple> apples, AppleFilter appleFilter) {
        List<Apple> appleList = new ArrayList<>();
        for (Apple apple : apples) {
            if (appleFilter.filter(apple)) {
                appleList.add(apple);
            }
        }
        return appleList;
    }

    public static void main(String[] args) throws InterruptedException {
        List<Apple> list = Arrays.asList(new Apple("green", 150), new Apple("yellow", 120), new Apple("green", 170));

        //常规方式
        List<Apple> result = findApple(list, new AppleFilter() {
            @Override
            public boolean filter(Apple apple) {
                return "green".equals(apple.getColor());
            }
        });

        //使用lambda方式
        List<Apple> lambdaResult1 = findApple(list, (Apple apple) -> {
            return apple.getColor().equals("green");
        });
        List<Apple> lambdaResult2 = findApple(list, apple -> apple.getColor().equals("green"));
        System.out.println(lambdaResult1);
        System.out.println(lambdaResult2);

        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
        Thread.currentThread().join();
    }
}
