package person.cobee.highperformanceprogramming.c2d2d2filterchain.filterchain;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-12-04
 */
public class PipelineDemo {

    // 整条责任链的头
    private HandlerChainContext head = new HandlerChainContext(new AbstractHandler() {
        @Override
        protected void doHandler(HandlerChainContext ctx, Object obj) {
            ctx.runNext(obj);
        }
    });

    // 开始处理请求
    public void requestProcess(Object obj){
        head.handler(obj);
    }

    // 把处理器增加到处理器链里
    public void addLast(AbstractHandler handler){
        HandlerChainContext ctx = head;
        while(ctx.hasNext()){
            ctx = ctx.getNext();
        }
        ctx.setNext(new HandlerChainContext(handler));
    }

    public static void main(String[] args) {
        PipelineDemo pipeline = new PipelineDemo();
        pipeline.addLast(new Handler1());
        pipeline.addLast(new Handler2());
        pipeline.requestProcess("你看看");
    }

}
