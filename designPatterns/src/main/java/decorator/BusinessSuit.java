package decorator;

/**
 * Created by youzhihao on 2017/2/27.
 * 西装
 */
public class BusinessSuit extends ClothDecorator {


    @Override
    public void wear() {
        super.wear();
        System.out.println("西装 ");
    }
}
