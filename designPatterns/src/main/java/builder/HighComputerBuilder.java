package builder;

/**
 * Created by youzhihao on 2017/3/5.
 */
public class HighComputerBuilder extends ComputerBuilder {

    @Override
    public String choiceCpu() {
        System.out.println("选择i7处理器");
        return "选择i7处理器";
    }

    @Override
    public Integer choiceRAM() {
        System.out.println("选择32g内存");
        return 32;
    }

}
