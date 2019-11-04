package person.cobee.highperformanceprogramming.c1d1d3;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-04
 */
public class CalcRunnable implements Runnable {

    private int i = 0;
    private int j = 0;

    @Override
    public void run() {
        i++;
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        j++;
    }

    public void print(){
        System.out.println("i=" + i + ", j=" + j);
    }

}
