package person.cobee.highperformanceprogramming.c2d2d2filterchain.filterchain;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-12-04
 */
public class Handler2 extends AbstractHandler {

    @Override
    protected void doHandler(HandlerChainContext ctx, Object obj) {
        obj = obj.toString() + "->handler2";
        System.out.println(obj);
        // 通过上下文执行下一个处理器
        ctx.runNext(obj);
        System.out.println("back:handler2");
    }

}
