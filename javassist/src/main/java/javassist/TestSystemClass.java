package javassist;

import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.util.HotSwapper;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by youzhihao on 2018/10/15.
 * 系统类不能这样动态修改，因为这里获取的是class loader，而系统类是通过system class loader加载的，详见双亲委派模型
 *
 */
public class TestSystemClass {

    public static void main(String[] args) throws Exception {
        //dynamicModifyTest();
        //staticModifyClassFile();
        staticModifyTest();
    }

    /**
     * 动态修改测试
     * 结果失败
     * -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000
     */
    public static void dynamicModifyTest() throws Exception {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis());
            }
        }, 0, 1, TimeUnit.SECONDS);
        Thread.sleep(1000);
        HotSwapper hotSwapper = new HotSwapper(8000);
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("java.lang.System");
        CtMethod ctMethod = ctClass.getDeclaredMethod("currentTimeMillis");
        ctMethod.setBody("{return 1111L; }");
        byte[] content = ctClass.toBytecode();
        hotSwapper.reload("java.lang.System", content);
        System.out.println(System.currentTimeMillis());
    }

    /**
     * 静态修改测试
     * 第一步:修改并生成对应的class,
     *
     */
    public static void staticModifyClassFile() throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        // try modify system native method: System.currentTimeMillis
        CtClass ctClass = classPool.get("java.lang.System");
        CtMethod ctMethod = ctClass.getDeclaredMethod("currentTimeMillis");
        ctMethod.setName("currentTimeMillis1");
        //try build a new currentTimeMillis method
        CtMethod newMethod = CtNewMethod.make("public static long currentTimeMillis(){return 111L;}", ctClass);
        ctClass.addMethod(newMethod);
        // try modify system normal static method:getenv()
        CtMethod ctMethod1 = ctClass.getDeclaredMethod("getenv", null);
        ctMethod1.setBody("{java.util.Map map = new java.util.HashMap();map.put(\"name\",\"youzhihao\");return map;}");
        //build the new System.class
        ctClass.writeFile("/Users/youzhihao/IdeaProjects/All-Parent/javassist/class/");
    }

    /**
     * 静态修改测试
     * 第二部:静态覆盖系统类
     * 结论:失败，native方法不能覆写，不能改名
     * -Xbootclasspath/p:/Users/youzhihao/IdeaProjects/All-Parent/javassist/class
     */
    public static void staticModifyTest() throws Exception {
        System.out.println(System.currentTimeMillis());
        System.out.println(System.getenv());
    }


}
