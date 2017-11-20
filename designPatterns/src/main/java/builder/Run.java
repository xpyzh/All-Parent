package builder;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by youzhihao on 2017/3/5.
 * 建造者模式
 * 主要用于创建一些复杂的对象，这些对象的内部构建间的建造顺序通常是稳定的，但内部对象的构建通常面临着复杂的变化
 * 个人感觉：建造者模式和模板模式很类似，建造模式多了一个指挥者角色，用来来固定建造的步骤
 */
public class Run {

    public static void main(String[] args) {

        Computer highConputer = new HighComputerBuilder().build();
        System.out.println(JSONObject.toJSONString(highConputer));
        Computer lowComputer = new LowComputerBuilder().build();
        System.out.println(JSONObject.toJSONString(lowComputer));

    }

}
