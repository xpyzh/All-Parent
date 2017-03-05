package builder;

/**
 * Created by youzhihao on 2017/3/5.
 */
public class HighComputerBuilder extends ComputerBuilder {

    @Override
    public void choiceCpu() {
        System.out.println("选择i7处理器");
        computer.setCpu("i7处理器");
    }

    @Override
    public void choiceRAM() {
        System.out.println("选择32g内存");
        computer.setRam(32);
    }

}
