package person.cobee.highperformanceprogramming.c1d1d3;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-04
 */
public class ThreadFlagStopTest {

    public static void main(String[] args) throws InterruptedException {
        FlagThread flagThread = new FlagThread();
        flagThread.start();
        Thread.sleep(1000);
        //flagThread.stop(); // 强制中止，直接销毁线程
        flagThread.flag = false; // 通过标记位来终止线程
        while(flagThread.isAlive()){

        }
        flagThread.print();
    }

}
