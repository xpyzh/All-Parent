package async;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by youzhihao on 2019/3/29.
 * 一个异步执行的例子，给定一个商品，查询多个店铺的价格
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {
        //demo1();
        demo3();
        demo2();
        demo4();


    }

    //顺序查询
    public static void demo1() {
        String product = "iphone xr";
        List<String> shopList = initShop();
        long startTime = System.currentTimeMillis();
        shopList.stream().forEach(shop -> {
            System.out.println(String.format("%s price is %.2f",
                    shop, findPrice(shop, product)));
        });
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("-------流的顺序查询总耗时-------: " + duration);
    }

    //使用流的并行处理
    public static void demo2() {
        String product = "iphone xr";
        List<String> shopList = initShop();
        long startTime = System.currentTimeMillis();
        shopList.stream().parallel().forEach(shop -> {
            System.out.println(String.format("%s price is %.2f",
                    shop, findPrice(shop, product)));
        });
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("-------流的并行查询总耗时-------: " + duration);
    }

    //使用CompletableFuture查询，他的优势是可以定制执行器
    public static void demo3() {
        String product = "iphone xr";
        List<String> shopList = initShop();
        long startTime = System.currentTimeMillis();
        List<CompletableFuture> futureList = shopList.stream().map(shop ->
                CompletableFuture.supplyAsync(() -> {
                    System.out.println(String.format("%s price is %.2f",
                            shop, findPrice(shop, product)));
                    return null;
                })
        ).collect(Collectors.toList());
        futureList.stream().forEach(CompletableFuture::join);
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("-------CompletableFuture查询总耗时-------: " + duration);
    }

    //使用CompletableFuture并且定制线程池查询
    public static void demo4() {
        String product = "iphone xr";
        List<String> shopList = initShop();
        Executor executor = Executors.newFixedThreadPool(Math.min(shopList.size(), 100));
        long startTime = System.currentTimeMillis();
        List<CompletableFuture> futureList = shopList.stream().map(shop ->
                CompletableFuture.supplyAsync(() -> {
                    System.out.println(String.format("%s price is %.2f",
                            shop, findPrice(shop, product)));
                    return null;
                }, executor)
        ).collect(Collectors.toList());
        futureList.stream().

                forEach(CompletableFuture::join);

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("-------CompletableFuture并且定制线程池查询总耗时-------: " + duration);
    }

    public static List<String> initShop() {
        return Arrays.asList("天猫", "京东", "严选", "拼多多", "考拉", "苏宁", "淘宝", "小红书", "当当");
    }

    public static double findPrice(String shop, String product) {
        delay();
        double price = new Random().nextDouble() * product.charAt(0) + product.charAt(1);
        return price;
    }

    //模拟查询价格的延迟
    public static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
