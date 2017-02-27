package decorator;

/**
 * Created by youzhihao on 2017/2/27.
 * 短裤
 */
public class Shorts extends ClothDecorator {

    public Shorts(Cloth cloth) {
        super(cloth);
    }

    @Override
    public void wear() {
        super.wear();
        System.out.println("短裤  ");
    }
}
