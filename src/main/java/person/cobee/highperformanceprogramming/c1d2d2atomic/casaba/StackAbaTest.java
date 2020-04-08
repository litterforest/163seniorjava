package person.cobee.highperformanceprogramming.c1d2d2atomic.casaba;

import person.cobee.vo.Node;

import java.util.concurrent.locks.LockSupport;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-08
 */
public class StackAbaTest {

    public static void main(String[] args) throws InterruptedException {
//        Stack stack = new Stack();
        ConcurrentStack stack = new ConcurrentStack();
        // 先入栈两个元素
        stack.push(new Node("A"));
        stack.push(new Node("B"));

        Thread t1 = new Thread(() -> {
            Node node = stack.pop(1000 * 1000 * 800L); //
            System.out.println(Thread.currentThread().getName() + ": " + node.getName());
            System.out.println(Thread.currentThread().getName() + " done...");
        });
        t1.start();
        LockSupport.parkNanos(1000 * 1000 * 200L);
        Thread t2 = new Thread(() -> {
            Node nodeB = stack.pop(0); // B出栈
            System.out.println(Thread.currentThread().getName() + ": " + nodeB.getName());
            Node nodeA = stack.pop(0); // A出栈
            System.out.println(Thread.currentThread().getName() + ": " + nodeA.getName());

            stack.push(new Node("D"));
            stack.push(new Node("C"));
            stack.push(nodeB);
            System.out.println(Thread.currentThread().getName() + " done...");
        });
        t2.start();

        t1.join();
        t2.join();
        System.out.println("=========================================================================");
        Node node = null;
        do{
            node = stack.pop(0);
            if(node != null){
                System.out.println(node.getName());
            }
        }while(node != null);

    }

}
