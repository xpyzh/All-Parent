package adapter;

/**
 * Created by youzhihao on 2017/11/20. 中国球员在nba打球的适配器
 */
public class ChinaNbaPlayerAdapter implements NBA {
  private NBAPlayer nbaPlayer=new NBAPlayer();

  @Override
  public void playBasketball() {
    System.out.println("翻译~~~~~");
    nbaPlayer.playBasketball();

  }
}
