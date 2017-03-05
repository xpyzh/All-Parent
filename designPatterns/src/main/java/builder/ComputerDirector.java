package builder;

/**
 * Created by youzhihao on 2017/3/5.
 * 指挥者，用来封装固定的电脑建造步骤
 */
public class ComputerDirector {

    private ComputerBuilder computerBuilder;

    public ComputerDirector(ComputerBuilder computerBuilder) {
        this.computerBuilder = computerBuilder;
    }

    public Computer build() {
        computerBuilder.init();
        computerBuilder.choiceCpu();
        computerBuilder.choiceRAM();
        return computerBuilder.build();
    }

}
