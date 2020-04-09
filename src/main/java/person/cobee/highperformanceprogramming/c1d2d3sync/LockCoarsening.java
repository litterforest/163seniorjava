package person.cobee.highperformanceprogramming.c1d2d3sync;

/**
 * 锁粗化，同步代码块合并
 *
 * @author cobee
 * @since 2020-04-09
 */
public class LockCoarsening {

    public void test(){
        int i = 0;
        synchronized (this){
            i++;
        }
        synchronized (this){
            i--;
        }
        synchronized (this){
            for(;;){
                i++;
                if(i > 10000){
                    break;
                }
            }
        }

        // 上面三段代码，等同于下面这一段化码。由JIT编译器代为优化
        synchronized(this){
            i++;
            i--;
            for(;;){
                i++;
                if(i > 10000){
                    break;
                }
            }
        }

    }

}
