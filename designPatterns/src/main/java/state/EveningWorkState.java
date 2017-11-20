package state;

/**
 * Created by youzhihao on 2017/11/20.
 */
public class EveningWorkState implements WorkState {
  @Override
  public void work(WorkContext workContext) {
    System.out.println("晚上工作~~~~，效率极差");
  }
}
