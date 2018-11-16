import java.io.File;
import java.net.URL;

import loader.MyUrlClassLoader;

/**
 * Created by youzhihao on 2018/11/8.
 * 这里主要验证如下几个问题:
 * 1.同一个classloader能加载同一个class吗? 不能
 * 2.父类和子类能够加载同一个class吗?
 *      a.子类先加载? 可以
 *      b.父类先加载? 可以
 * 3.父lassloader加载的类，其中该类依赖的只在子类lassloader中存在，这样可行吗?不能
 *    结论:一个classLoader加载一个类，其依赖的查找是在加载这个类的classloader及其父classloader中查找
 * 4.子lassloader加载的类, 其中该类依赖在父lassloader中存在，这样可行吗? 可行
 * 5.Bootstrap ClassLoader和appclassLoader同时加载一个类，new出来的时候哪个classLoader加载的？根据单亲委派模型，优先获取父Bootstrap ClassLoader加载的
 *
 * 分别以几个demo类验证如上问题:lib目录下有两个class,ParentBean和ChildBean,其中ChildBean的加载依赖ParentBean
 */
public class Test {


    public static void main(String[] args) throws Exception {
        //demo1();
        //demo2();
        demo3();
        //demo4();
        //demo5();
    }

    /**
     * 验证第一个问题，改写了urlClassLoader,使得正真的加载class的方法findClass暴露出来供直接调用
     * 默认urlClassLoader.loadClass()的逻辑如下:
     * 1.先查看缓存有没有该class
     * 2.委托给父类查找,同样父类也执行1，2，3逻辑
     * 3.没有则自己查找
     * @throws Exception
     */
    public static void demo1() throws Exception {
        URL[] url = new URL[1];
        url[0] = new File("/Users/youzhihao/IdeaProjects/All-Parent/classloader/lib/").toURI().toURL();
        MyUrlClassLoader urlClassLoader = new MyUrlClassLoader(url, Test.class.getClassLoader(), "");
        urlClassLoader.findClass("ChildBean");
        urlClassLoader.findClass("ChildBean");
    }

    /**
     * 验证第二个问题，父和子classloader可以同时加载相同的类
     * @throws Exception
     */
    public static void demo2() throws Exception {
        URL[] url = new URL[1];
        url[0] = new File("/Users/youzhihao/IdeaProjects/All-Parent/classloader/lib/parent").toURI().toURL();
        MyUrlClassLoader loaderParent = new MyUrlClassLoader(url, Test.class.getClassLoader(), "parent");
        MyUrlClassLoader loaderChild = new MyUrlClassLoader(url, loaderParent, "child");
        // child classloader load the class in the first
        Class c1 = loaderChild.findClass("ParentBean");
        Class c2 = loaderParent.findClass("ParentBean");
        System.out.println(c1 == c2);
        System.out.println(c1.getClassLoader().toString());
        System.out.println(c2.getClassLoader().toString());


    }

    /**
     * 验证第三个问题，子classLoader中加载的ChildBean依赖父classLoader中的parentBean
     * @throws Exception
     */
    public static void demo3() throws Exception {
        //父classLoader加载ParentBean
        URL[] parentUrl = new URL[1];
        parentUrl[0] = new File("/Users/youzhihao/IdeaProjects/All-Parent/classloader/lib/parent").toURI().toURL();
        //子classLoader加载ChildBean
        MyUrlClassLoader parentLoader = new MyUrlClassLoader(parentUrl, Test.class.getClassLoader(), "parent");
        URL[] childUrl = new URL[1];
        childUrl[0] = new File("/Users/youzhihao/IdeaProjects/All-Parent/classloader/lib/child").toURI().toURL();
        MyUrlClassLoader childLoader = new MyUrlClassLoader(childUrl, parentLoader, "child");
        try {
            childLoader.loadClass("ParentBean").newInstance();
            System.out.println("child classloader new ParentBean success");
        } catch (Throwable e) {
            System.out.println("child classloader new ParentBean fail");
        }
        try {
            childLoader.loadClass("ChildBean").newInstance();
            System.out.println("child classloader new ChildBean success");
        } catch (Throwable e) {
            System.out.println("child classloader new ChildBean fail");
        }
        try {
            parentLoader.loadClass("ParentBean").newInstance();
            System.out.println("parent classloader new ParentBean success");
        } catch (Throwable e) {
            System.out.println("parent classloader new ParentBean fail");
        }
        try {
            parentLoader.loadClass("ChildBean").newInstance();
            System.out.println("parent classloader new ChildBean success");
        } catch (Throwable e) {
            System.out.println("parent classloader new ChildBean fail");
        }
    }

    /**
     * 验证第三个问题，父classLoader中加载的ChildBean依赖子classLoader中的parentBean
     * @throws Exception
     */
    public static void demo4() throws Exception {
        //父classLoader加载ChildBean
        URL[] parentUrl = new URL[1];
        parentUrl[0] = new File("/Users/youzhihao/IdeaProjects/All-Parent/classloader/lib/child").toURI().toURL();
        //子classLoader加载ParentBean
        MyUrlClassLoader parentLoader = new MyUrlClassLoader(parentUrl, Test.class.getClassLoader(), "parent");
        URL[] childUrl = new URL[1];
        childUrl[0] = new File("/Users/youzhihao/IdeaProjects/All-Parent/classloader/lib/parent").toURI().toURL();
        MyUrlClassLoader childLoader = new MyUrlClassLoader(childUrl, parentLoader, "child");
        //成功，子classloader能找到自己的ParentBean
        try {
            childLoader.loadClass("ParentBean").newInstance();
            System.out.println("child classloader new ParentBean success");
        } catch (Throwable e) {
            System.out.println("child classloader new ParentBean fail");
        }
        //失败,子classloader能找到父的childBean,然后从父中开始找相关依赖的bean，发现没有找到parentBean,这个地方非常重要，不会再从子开始找
        //一个classLoader加载一个类，其依赖的查找是在加载这个类的classloader及其父classloader中查找
        try {
            childLoader.loadClass("ChildBean").newInstance();
            System.out.println("child classloader new ChildBean success");
        } catch (Throwable e) {
            System.out.println("child classloader new ChildBean fail");
        }
        try {
            parentLoader.loadClass("ParentBean").newInstance();
            System.out.println("parent classloader new ParentBean success");
        } catch (Throwable e) {
            System.out.println("parent classloader new ParentBean fail");
        }
        try {
            parentLoader.loadClass("ChildBean").newInstance();
            System.out.println("parent classloader new ChildBean success");
        } catch (Throwable e) {
            System.out.println("parent classloader new ChildBean fail");
        }
    }

    //验证问题5
    //结论:符合双亲委派模型，User实例的化时Bootstrap ClassLoader优先加载了该类
    public static void demo5() throws Exception {
        User user = new User();
        System.out.println(user.toString());
        System.out.println(user.getName());
    }

    public static void demo6() throws Exception {

    }

}
