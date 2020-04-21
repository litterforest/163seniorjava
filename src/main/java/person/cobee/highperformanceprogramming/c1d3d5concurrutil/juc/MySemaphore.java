package person.cobee.highperformanceprogramming.c1d3d5concurrutil.juc;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-04-21
 */
public class MySemaphore {

    private Sync sync;

    public MySemaphore(int permit){
        sync = new Sync(permit);
    }

    public void acquire(){
        sync.acquireShared(1);
    }

    public void release(){
        sync.releaseShared(1);
    }

    class Sync extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = 5171687685982861397L;

        public Sync(int permits){ // 同一时间允许多少个线程同时操作数据库
            setState(permits);
        }

        /**
         * 获取锁的算法
         * @param arg
         * @return
         */
        @Override
        protected int tryAcquireShared(int arg) {
            for(;;){ // 自旋配合cas操作
                int available = getState(); // 有多少个可用的
                int remaing = available - arg; // 还剩下多少个
                if(remaing < 0 || compareAndSetState(available, remaing))
                    return remaing;
            }
        }

        /**
         * 释放锁的算法
         * @param arg
         * @return
         */
        @Override
        protected boolean tryReleaseShared(int arg) {
            for(;;){
                int current = getState();
                int nextCurrent = current + arg;
                if(nextCurrent < current)
                    throw new Error("超过了最大允许计数");
                if(compareAndSetState(current, nextCurrent))
                    return true;
            }
        }
    }

}
