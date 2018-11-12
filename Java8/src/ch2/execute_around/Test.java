package ch2.execute_around;

import ch2.lambda.Apple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

public class Test {

    public static String processFile() throws IOException {
        // 使用带资源的try语句，不需要显示关闭资源
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return br.readLine();
        }
    }

    public static String processFile2(BufferedReaderProcessor processor) throws IOException {
        // 使用带资源的try语句，不需要显示关闭资源
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return processor.process(br);
        }
    }

    // Predicate断言式接口使用
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    // Consumer消费型函数式接口
    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }

    // Function功能型函数式接口(接受T，转化为R)
    public static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(function.apply(t));
        }
        return result;
    }

    // BinaryOperator函数式接口(接受两个T，返回一个结果T)
    public static <T> T calculate(T t1, T t2, BinaryOperator<T> operator) {
        return operator.apply(t1, t2);
    }

    public static void main(String[] args) throws IOException {
        // 读取一行
        // 相当于实现了BufferedReaderProcessor接口中的process()方法
        //String oneLine = processFile2((BufferedReader br) -> br.readLine());
        // 读取两行
        //String twoLine = processFile2((bufferedReader -> bufferedReader.readLine() + bufferedReader.readLine()));

        List<String> result = filter(Arrays.asList("", "a", "b"), (String str) -> !str.isEmpty());

        forEach(Arrays.asList(1, 2, 3, 4, 5), (Integer i) -> System.out.println(i));

        List<Integer> length = map(Arrays.asList("lam", "bd", "as"), (String s) -> s.length());

        int num = calculate(2, 3, (Integer t1, Integer t2) -> t1 * t2);

        BiPredicate<List<String>, String> contains1 = List::contains;
        BiPredicate<List<String>, String> contains2 = (List<String> list, String element) -> list.contains(element);

        BiFunction<Integer, String, Apple> c3 = Apple::new;
        c3.apply(12, "green");
    }
}
