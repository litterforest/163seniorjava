package person.cobee.highperformanceprogramming.c1d2d2atomic.casaba;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import person.cobee.vo.Node;

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
     * 入栈操作
     * @param newNode
     */
    public void push(Node newNode){
        Node oldTop = null;
        do {
            oldTop = topRef.get(); // 第一次操作返回null
            newNode.setNext(oldTop); // setNext就是null
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
            if(timeoutNanos > 0){
                LockSupport.parkNanos(timeoutNanos);
            }
            newTop = oldTop.getNext();
            oldTop.setNext(null);
        }while(!topRef.compareAndSet(oldTop, newTop));
        return oldTop;
    }

}
