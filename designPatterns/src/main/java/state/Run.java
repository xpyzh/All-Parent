package state;

/**
 * Created by youzhihao on 2017/11/20. 状态模式：主要解决的是当控制一个对象状态转换的条件表达式过于复杂时的情况。把状态的判断逻辑转移到表示
 * 不同状态的一系列类当中，可以把复杂的判断逻辑简化
 */
public class Run {
  public static void main(String[] args) {
    WorkContext workContext = new WorkContext(6);
    workContext.work();
    workContext.setTime(13);
    workContext.work();
    workContext.setTime(20);
    workContext.work();
  }
}
