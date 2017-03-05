package builder;

import java.text.MessageFormat;

/**
 * Created by youzhihao on 2017/3/5.
 */
public abstract class ComputerBuilder {

    protected Computer computer;

    //初始化
    void init() {
        computer = new Computer();
        System.out.println("~~~~选择配件中~~~~");
    }

    //选择cpu
    abstract void choiceCpu();

    //选择内存
    abstract void choiceRAM();


    Computer build() {
        System.out.println("~~~~组装成功~~~~");
        System.out.println(MessageFormat.format("处理器:{0}", computer.getCpu()));
        System.out.println(MessageFormat.format("内存大小:{0}", computer.getRam()));
        return computer;
    }

}
