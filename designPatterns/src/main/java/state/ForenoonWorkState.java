package state;

/**
 * Created by youzhihao on 2017/11/20. 早上工作
 */
public class ForenoonWorkState implements WorkState {
  @Override
  public void work(WorkContext workContext) {
    if (workContext.getTime() > 12) {
      workContext.setWorkState(new NoonWorkState());
      workContext.work();
    } else {
      System.out.println("早上工作~~~~，效率高");
    }

  }
}
