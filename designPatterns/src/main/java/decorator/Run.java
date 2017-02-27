package decorator;

/**
 * Created by youzhihao on 2017/2/27.
 * 装饰着模式的demo：
 * 搭配不同的穿衣风格
 */
public class Run {

    public static void main(String[] args) {
        style1();
        style2();

    }
    public static void style1()
    {
        //西装+牛仔裤
        Person person=new Person("尤智浩");
        BusinessSuit businessSuit=new BusinessSuit(person);
        Jeans jeans=new Jeans(businessSuit);
        jeans.wear();
    }
    public static void style2()
    {
        //西装+短裤
        Person person=new Person("尤智浩");
        BusinessSuit businessSuit=new BusinessSuit(person);
        Shorts jeans=new Shorts(businessSuit);
        jeans.wear();
    }

}
