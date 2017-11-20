package builder;

/**
 * Created by youzhihao on 2017/3/5.
 */
public abstract class ComputerBuilder {


    //选择cpu
    abstract String choiceCpu();

    //选择内存
    abstract Integer choiceRAM();


    public  Computer build() {
        System.out.println("------组装开始------");
        Computer computer = new Computer();
        computer.setCpu(choiceCpu());
        computer.setRam(choiceRAM());
        return computer;
    }

}
