package builder;

/**
 * Created by youzhihao on 2017/3/5.
 */
public class LowComputerBuilder extends ComputerBuilder {


    @Override
    public String choiceCpu() {
        System.out.println("选择奔腾处理器");
        return "选择奔腾处理器";
    }

    @Override
    public Integer choiceRAM() {
        System.out.println("选择2g内存");
        return 2;
    }

}
