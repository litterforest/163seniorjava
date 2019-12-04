package person.cobee.highperformanceprogramming.c2d2d2filterchain.filterchain;

/**
 * 抽象处理器
 *
 * @author cobee
 * @since 2019-12-04
 */
public abstract class AbstractHandler {

    /**
     *
     * @param ctx 链的维护与处理请求的传递
     * @param obj 处理的请求对象
     */
    protected abstract void doHandler(HandlerChainContext ctx, Object obj);

}
