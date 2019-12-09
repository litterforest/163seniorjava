package person.cobee.highperformanceprogramming.c3d1d1classloader;

/**
 * 查看类加载器，三大加载器：Bootstrap ClassLoader(用c语言写，加载rt.jar的类), ExtClassLoader(加载扩展包的类),
 * AppClassLoader(加载项目工程中的类)
 *
 * @author cobee
 * @since 2019-12-09
 */
public class ViewClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
        // ViewClassLoader的类加载器
        System.out.println("ViewClassLoader的类加载器:" + ViewClassLoader.class.getClassLoader());
        // bootstrap classloader 核心类库加载器
        System.out.println("核心类库加载器:" + ViewClassLoader.class.getClassLoader().loadClass("java.lang.String").getClassLoader());
        // 扩展类库加载器
        System.out.println("扩展类库加载器:" + ViewClassLoader.class.getClassLoader().loadClass("com.sun.nio.zipfs.ZipCoder").getClassLoader());
        // 双亲委派模型 Parents Delegation Model
        System.out.println("应用程序库加载器的父类：" + ViewClassLoader.class.getClassLoader().getParent());
        System.out.println(
                "应用程序库加载器的父类的父类：" + ViewClassLoader.class.getClassLoader().getParent().getParent());
    }

}
