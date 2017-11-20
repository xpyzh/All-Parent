package adapter;

/**
 * Created by youzhihao on 2017/11/20. 适配器模式
 */
public class Run {
  public static void main(String[] args) {

    NBA nbaPlayer=new ChinaNbaPlayerAdapter();
    nbaPlayer.playBasketball();

  }
}
