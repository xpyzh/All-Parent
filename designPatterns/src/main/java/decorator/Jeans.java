package decorator;

/**
 * Created by youzhihao on 2017/2/27.
 * 牛仔裤
 */
public class Jeans extends ClothDecorator {

    public Jeans(Cloth cloth) {
        super(cloth);
    }

    @Override
    public void wear() {
        super.wear();
        System.out.println("牛仔裤 ");

    }
}
