package javassist;

import javassist.util.HotSwapper;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by youzhihao on 2018/10/15.
 * 普通类的动态修改
 * 开启jpda允许动态加载class,-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000
 */
public class TestNormalClass {

    public static void main(String[] args) throws Exception {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //System.out.println(System.currentTimeMillis());
                User.show();
            }
        }, 0, 1, TimeUnit.SECONDS);
        Thread.sleep(1000);
        HotSwapper hotSwapper = new HotSwapper(8000);
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("javassist.User");
        CtMethod ctMethod = ctClass.getDeclaredMethod("show");
        ctMethod.setBody("{ System.out.println(\"youzhihao\"); }");
        byte[] content = ctClass.toBytecode();
        hotSwapper.reload("javassist.User", content);
    }

}
