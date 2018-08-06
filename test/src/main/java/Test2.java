/**
 * Created by youzhihao on 2018/6/24.
 */
public class Test2 {

    public static void main(String[] args) throws Exception {
        boolean flag = test();
        //transferService.transfer("zhangsan", "lisi", 10, UuidGenUtil.create());
//        recover();
//        delete();
    }

    public static boolean test() {
        try {
            throw new RuntimeException();
        } finally {
            return true;
        }

    }


}
