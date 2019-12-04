package person.cobee.highperformanceprogramming.c2d2d2filterchain.filterchain;

/**
 * 主要是维护链和链的执行
 *
 * @author cobee
 * @since 2019-12-04
 */
public class HandlerChainContext {

    private HandlerChainContext next;
    private AbstractHandler handler;

    public HandlerChainContext(AbstractHandler handler){
        this.handler = handler;
    }

    // 执行下一个处理器
    public void runNext(Object obj){
        if(next != null){
            next.handler(obj);
        }
    }

    public void handler(Object arg0) {
        this.handler.doHandler(this, arg0);
    }

    // 是否存在下一个处理器
    public boolean hasNext(){
        return next != null;
    }

    public HandlerChainContext getNext(){
        return this.next;
    }

    public void setNext(HandlerChainContext next){
        this.next = next;
    }

}
