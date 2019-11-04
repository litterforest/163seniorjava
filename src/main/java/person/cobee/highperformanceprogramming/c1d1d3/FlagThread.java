package person.cobee.highperformanceprogramming.c1d1d3;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2019-11-04
 */
public class FlagThread extends Thread {

    private int i;
    private int j;
    public volatile boolean flag = true;

    @Override
    public void run() {
        i++;
        while(flag){

        }
        j++;
    }

    public void print(){
        System.out.println("i=" + i + ", j=" + j);
    }

}
