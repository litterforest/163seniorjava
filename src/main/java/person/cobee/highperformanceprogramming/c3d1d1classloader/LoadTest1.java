package person.cobee.highperformanceprogramming.c3d1d1classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 类的卸载
 *
 * @author cobee
 * @since 2019-12-10
 */
public class LoadTest1 {

    public static void main(String[] args) throws Exception{
        URL url = new URL("file:D:\\");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        // 加载类时不执行静态初始化块，类被第一次使用的时候会执行，比如new一个对象，或直接调用常量
        Class clazz = urlClassLoader.loadClass("HelloWorld");
        System.out.println("HelloWorld的类加载器:" + clazz.getClassLoader());
        Object obj = clazz.newInstance();
        clazz.getMethod("test").invoke(obj);
        Thread.sleep(3000L);
        obj = null; // 对象实例置为空
        urlClassLoader = null; // 类加载器也置为空
        System.gc();
        Thread.sleep(10000L);
    }

}
