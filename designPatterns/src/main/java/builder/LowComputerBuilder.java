package builder;

/**
 * Created by youzhihao on 2017/3/5.
 */
public class LowComputerBuilder extends ComputerBuilder {


    @Override
    public void choiceCpu() {
        System.out.println("选择奔腾处理器");
        computer.setCpu("奔腾处理器");
    }

    @Override
    public void choiceRAM() {
        System.out.println("选择2g内存");
        computer.setRam(2);
    }

}
