package person.cobee.highperformanceprogramming.c1d3d2aqs.templatepattern;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-13
 */
public class PPT01 extends MotherMash {
    @Override
    void title() {
        System.out.println("AQS抽象队列同步器");
    }

    @Override
    void content() {
        System.out.println("读写锁使用、自己实现ConcurrentHashMap、锁降级(在写锁里面再次获取读锁)");
    }

    @Override
    void footer() {
        System.out.println("网易高级java课程");
    }
}
