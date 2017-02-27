package decorator;

/**
 * Created by youzhihao on 2017/2/27.
 * 服饰抽象类
 */
public class ClothDecorator implements Cloth {

    private Cloth cloth;

    //这里的构造函数初始化cloth，可以改成set方法
    public ClothDecorator(Cloth cloth) {
        this.cloth = cloth;
    }

    @Override
    public void wear() {
        if (cloth != null) {
            cloth.wear();
        }

    }
}
