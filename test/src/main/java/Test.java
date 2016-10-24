/**
 * Created by youzhihao on 2016/9/27.
 */
public class Test {

    public static void main(String[] args) throws Exception {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("关闭资源");
            }
        }));
        while (true) {
            Thread.currentThread().sleep(1000);
            System.out.println("程序结束");
        }
    }

}
