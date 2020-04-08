package person.cobee.highperformanceprogramming.c1d2d2atomic.casaba;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import person.cobee.vo.Node;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
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
public class ConcurrentStack implements Serializable {

    private static final long serialVersionUID = 5550872414200951949L;
//    AtomicReference<Node> topRef = new AtomicReference<>();
    AtomicStampedReference<Node> topRef = new AtomicStampedReference<>(null, 0);
    /**
     * 入栈操作
     * @param newNode
     */
    public void push(Node newNode){
        Node oldTop = null;
        int v;
        do {
            v = topRef.getStamp();
            oldTop = topRef.getReference(); // 第一次操作返回null
            newNode.setNext(oldTop); // setNext就是null
        }while (!topRef.compareAndSet(oldTop, newNode, v, v+1));
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
        int v;
        do {
            v = topRef.getStamp();
            oldTop = topRef.getReference();
            if(oldTop == null){    // 如果没有值，就返回null
                return null;
            }
            if(timeoutNanos > 0){
                LockSupport.parkNanos(timeoutNanos);
            }
            newTop = oldTop.getNext();
            oldTop.setNext(null);
        }while(!topRef.compareAndSet(oldTop, newTop, v, v+1));
        return oldTop;
    }

}
