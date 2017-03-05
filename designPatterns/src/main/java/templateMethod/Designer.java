package templateMethod;

/**
 * Created by youzhihao on 2017/3/5.
 */
public abstract class Designer {

    //设计一个产品
    public void designProject() {
        System.out.println("需求调研");
        System.out.println("需求分析");
        System.out.println("库表设计");
        program();
    }

    //具体编程
    protected abstract void program();


}
