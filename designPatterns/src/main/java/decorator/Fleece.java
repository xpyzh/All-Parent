package decorator;

/**
 * Created by youzhihao on 2017/2/27.
 * 卫衣
 */
public class Fleece extends ClothDecorator {

    public Fleece(Cloth cloth) {
        super(cloth);
    }

    @Override
    public void wear() {
        super.wear();
        System.out.println("卫衣  ");
    }
}
