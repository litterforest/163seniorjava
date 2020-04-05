package person.cobee.highperformanceprogramming.c1d2d1;

/**
 * 多线程的可见性
 *
 * @author cobee
 * @since 2020-04-04
 */
public class Demo1Visibility {

    int i = 0;
    volatile boolean isRunning = true;

    public static void main(String[] args) throws InterruptedException {
        Demo1Visibility demo = new Demo1Visibility();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开始计数");
                while(demo.isRunning){
                    demo.i = 123;
                }
                System.out.println(demo.i);
            }
        });
        thread.start();
        Thread.sleep(3000L);
        demo.isRunning = false;
        System.out.println("关闭主程序");
    }

}
