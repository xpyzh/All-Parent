package state;

/**
 * Created by youzhihao on 2017/11/20.
 */
public class NoonWorkState implements WorkState {
  @Override
  public void work(WorkContext workContext) {
    if (workContext.getTime() > 18) {
      workContext.setWorkState(new EveningWorkState());
      workContext.work();
    } else {
      System.out.println("下午工作~~~~~，效率一般");
    }
  }
}
