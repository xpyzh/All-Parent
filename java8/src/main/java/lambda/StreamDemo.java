package lambda;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by youzhihao on 2019/3/26.
 * 流的练习题
 */
public class StreamDemo {

    public static void main(String[] args) {
        exercise1();
        exercise2();
        exercise3();
        exercise4();
        exercise5();
        exercise6();
        exercise7();
        exercise8();
    }

    //初始化
    public static List<Transaction> initTransaction() {
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
        return transactions;
    }

    //找出2011年发生的所有交易，并按交易额排序(从低到高)。
    public static void exercise1() {
        System.out.println("1.找出2011年发生的所有交易，并按交易额排序(从低到高):");
        List<Transaction> transactions = initTransaction();
        List<Transaction> result = transactions.stream().filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
        System.out.println(JSONObject.toJSONString(result));
    }

    //交易员都在哪些不同的城市工作过?
    public static void exercise2() {
        System.out.println("2.交易员都在哪些不同的城市工作过?:");
        List<Transaction> transactions = initTransaction();
        //解法1,distinct+toList
        List<String> result1 = transactions.stream().map(t -> t.getTrader().city).distinct().collect(Collectors.toList());
        //解法2,toSet
        Set<String> result2 = transactions.stream().map(t -> t.getTrader().city).collect(Collectors.toSet());
        System.out.println(JSONObject.toJSONString(result1));
        System.out.println(JSONObject.toJSONString(result2));

    }

    //查找所有来自于剑桥的交易员，并按姓名排序。
    public static void exercise3() {
        System.out.println("3.查找所有来自于剑桥的交易员，并按姓名排序。:");
        List<Transaction> transactions = initTransaction();
        List<Trader> result = transactions.stream().map(Transaction::getTrader).filter(t -> "Cambridge".equals(t.getCity()))
                .distinct().sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
        System.out.println(JSONObject.toJSONString(result));

    }

    //返回所有交易员的姓名字符串，按字母顺序排序。
    public static void exercise4() {
        System.out.println("4.返回所有交易员的姓名字符串，按字母顺序排序。:");
        List<Transaction> transactions = initTransaction();
        String result = transactions.stream().map(t -> t.getTrader().getName()).distinct()
                .sorted(Comparator.comparing(s -> s)).collect(Collectors.joining(","));
        System.out.println(JSONObject.toJSONString(result));
    }

    //有没有交易员是在米兰工作的?
    public static void exercise5() {
        System.out.println("5.有没有交易员是在米兰工作的?:");
        List<Transaction> transactions = initTransaction();
        boolean result = transactions.stream().anyMatch((Transaction t) -> "Milan".equals(t.getTrader().getCity()));
        System.out.println(JSONObject.toJSONString(result));
    }

    //打印生活在剑桥的交易员的所有交易额。
    public static void exercise6() {
        System.out.println("6.打印生活在剑桥的交易员的所有交易额:");
        List<Transaction> transactions = initTransaction();
        transactions.stream().filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue).forEach(System.out::println);

    }

    //所有交易中，最高的交易额是多少?
    public static void exercise7() {
        System.out.println("7.所有交易中，最高的交易额是多少:");
        List<Transaction> transactions = initTransaction();
        int result = transactions.stream().map(Transaction::getValue).reduce(0, Integer::max);
        System.out.println(JSONObject.toJSONString(result));
    }

    //找到交易额最小的交易。
    public static void exercise8() {
        System.out.println("8.找到交易额最小的交易:");
        List<Transaction> transactions = initTransaction();
        Optional<Integer> result = transactions.stream().map(Transaction::getValue).reduce(Integer::min);
        System.out.println(JSONObject.toJSONString(result));
    }

    public static class Trader {

        private final String name;

        private final String city;

        public Trader(String n, String c) {
            this.name = n;
            this.city = c;
        }

        public String getName() {
            return this.name;
        }

        public String getCity() {
            return this.city;
        }

        public String toString() {
            return "Trader:" + this.name + " in " + this.city;
        }
    }

    public static class Transaction {

        private final Trader trader;

        private final int year;

        private final int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return this.trader;
        }

        public int getYear() {
            return this.year;
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            return "{" + this.trader + ", " +
                    "year: " + this.year + ", " +
                    "value:" + this.value + "}";

        }
    }

}

