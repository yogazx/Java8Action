package ch5;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TradeTest {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 1.找出2011年的所有交易并按交易额排序(从低到高)
        List<Transaction> transactionList2011 = transactions.stream().filter(transaction -> transaction.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
        // 2.交易员都在哪些不同的城市工作过
        List<String> cities = transactions.stream().map(transaction -> transaction.getTrader().getCity()).distinct().collect(Collectors.toList());
        // 3.查找所有来自于剑桥的交易员，并按姓名排序
        List<Trader> traderList = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        // 4.返回所有交易员的姓名字符串，按字母顺序排序
        List<String> nameList = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        // 5.有没有交易员是在米兰工作的
        boolean isMilan = transactions.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        // 6.打印生活在剑桥的交易员的所有交易额
        transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge")).map(Transaction::getValue).forEach(System.out::println);
        // 7.所有交易中，最高的交易额是多少
        Optional<Integer> max = transactions.stream().map(Transaction::getValue).reduce(Integer::max);
        // 8.找到交易额最小的交易
        Optional<Transaction> min1 = transactions.stream().reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
        // 第二种写法，流也支持min,max方法
        Optional<Transaction> min2 = transactions.stream().min(Comparator.comparing(Transaction::getValue));

        //使用流查看一个文件中有多少不同的单词
        long uniqueWords = 0;
        try {
            // 使用nio的Files.lines转换成流
            Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset());
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 由函数生成流，创建无限流
        // Stream.iterate和Stream.generate
        Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);

        // 使用流生成斐波那契数列
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));
    }
}
