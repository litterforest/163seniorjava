package person.cobee.highperformanceprogramming.c1d3d2aqs.templatepattern;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-13
 */
public abstract class MotherMash {

    /**
     * 标题
     */
    abstract void title();

    /**
     * 内容
     */
    abstract void content();

    /**
     * 脚部
     */
    abstract void footer();

    public final void show(){
        title();
        content();
        footer();
    }

}
