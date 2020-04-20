package person.cobee.highperformanceprogramming.c1d3d5concurrutil.juc;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-20
 */
public final class MyCountDownLatch extends AbstractQueuedSynchronizer {

    public MyCountDownLatch(int count){
        setState(count);
    }

    public void await(){
        acquireShared(1);
    }

    public void countDown(){
        releaseShared(1);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return getState() == 0 ? 1 : -1;
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        for(;;){
            int count = getState();
            if(count == 0)
                return false;
            int decre = count - 1;
            if(compareAndSetState(count, decre)){
                return decre == 0;
            }
        }
    }
}
