package person.cobee.highperformanceprogramming.c1d2d3sync.cas.aba;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 会出现aba问题的栈
 *
 * @author cobee
 * @since 2019-11-19
 */
@Getter
@Setter
@ToString
public class Stack implements Serializable {

    private static final long serialVersionUID = 5550872414200951949L;
    AtomicReference<Node> topRef = new AtomicReference<>();

    /**
     * 栈顶对象，利用cas操作
     */
    private Node top;

    /**
     * 入栈操作
     * @param newNode
     */
    public void push(Node newNode){
        Node oldTop = null;
        do {
            oldTop = topRef.get();
            newNode.setNext(oldTop);
        }while (!topRef.compareAndSet(oldTop, newNode));
    }

    /**
     * 出栈操作，利用cas操作，利用延时模拟aba问题
     *
     * @param timeoutNanos 单位纳秒
     * @return
     */
    public Node pop(long timeoutNanos){
        Node oldTop = null;
        Node newTop = null;
        do {
            oldTop = topRef.get();
            if(oldTop == null){    // 如果没有值，就返回null
                return null;
            }
            if(timeoutNanos != 0){
                LockSupport.parkNanos(timeoutNanos);
            }
            newTop = oldTop.getNext();
            oldTop.setNext(null);
        }while(!topRef.compareAndSet(oldTop, newTop));
        return oldTop;
    }

}
