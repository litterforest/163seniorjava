package person.cobee.highperformanceprogramming.c3d1d1classloader;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 指定任意路径进行类加载和热加载
 * 1，类加载器每次都new一个对象，就会产生热加载的效果
 *
 * @author cobee
 * @since 2019-12-10
 */
public class LoadTest {

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InterruptedException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        URL url = new URL("file:D:\\");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url}); // 非热加载
        while(true){
//            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url}); // 热加载
            // 加载类时不执行静态初始化块，类被第一次使用的时候会执行，比如new一个对象，或直接调用常量
            Class clazz = urlClassLoader.loadClass("HelloWorld");
            System.out.println("HelloWorld的类加载器:" + clazz.getClassLoader());
//            Class clazzStr = urlClassLoader.loadClass("java.lang.String");
////            System.out.println("String的类加载器:" + clazzStr.getClassLoader());
            Object obj = clazz.newInstance();
            clazz.getMethod("test").invoke(obj);
            Thread.sleep(3000L);
        }
    }

}
