package state;

/**
 * Created by youzhihao on 2017/11/20.
 */
public class WorkContext {
  private int time;
  private WorkState workState;

  public WorkContext(int time) {
    this.time = time;
    workState=new ForenoonWorkState();
  }

  public void work() {
    workState.work(this);
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public WorkState getWorkState() {
    return workState;
  }

  public void setWorkState(WorkState workState) {
    this.workState = workState;
  }
}
