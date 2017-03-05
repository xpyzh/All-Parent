package templateMethod;

/**
 * Created by youzhihao on 2017/3/5.
 * 模板方法模式
 * 实际就是将通用的模板流程和方法写好，然后定义几个变化的抽象方法，共子类实现
 */
public class Run {

    public static void main(String[] args) {
        Designer javaDesigner = new JAVADesigner();
        System.out.println("java程序猿：");
        javaDesigner.designProject();
        Designer phpDesigner = new PHPDesigner();
        System.out.println("php程序猿：");
        phpDesigner.designProject();

    }

}
