package loader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * Created by youzhihao on 2018/11/8.
 * 这里的主要目的是重写下classloader,方便调试，加载的逻辑还是双亲委派
 */
public class MyUrlClassLoader extends URLClassLoader {
    private ClassLoader parent;

    private String name;

    public MyUrlClassLoader(URL[] urls, ClassLoader parent, String name) {
        super(urls, parent);
        this.parent = parent;
        this.name = name;
    }

    public MyUrlClassLoader(URL[] urls) {
        super(urls);
    }

    public MyUrlClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        c = parent.loadClass(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    public Class<?> findClass(final String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

}
