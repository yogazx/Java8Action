package ch3;

import ch2.lambda.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * complex lambda
 */
public class Test {

    public static void main(String[] args) {
        Comparator<Apple> comparator = Comparator.comparing(Apple::getWeight);

        List<Apple> inventory = Arrays.asList(new Apple(80,"green"), new Apple(155, "green"),
                new Apple(120, "red"));

        // 逆序
        inventory.sort(Comparator.comparing(Apple::getWeight).reversed());
        // 比较器链
        inventory.sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));

        // 谓词复合 （ negate,and,or）
        // 函数复合 (addThen(),compose()两个方法)
        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformatPipeline = addHeader.andThen(Letter::checkSpelling).andThen(Letter::addFooter);
    }
}
