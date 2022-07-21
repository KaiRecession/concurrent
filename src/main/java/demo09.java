import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 顺序控制模式，统一先等待再唤醒下一线程。一来有利于线程的结束，二来便于记忆。
 * 重点考虑第一个开头线程是否单独需要单独拿出来设置
 */
public class demo09 {
    static class SyncWaitNotify {
        private int flag;
        private int loopNumber;

        public SyncWaitNotify(int flag, int loopNumber) {
            this.flag = flag;
            this.loopNumber = loopNumber;
        }

        public void print(int flag, int nextFlag) {
            for (int i = 0; i < loopNumber; i++) {
                synchronized (this) {
                        while (flag != this.flag) {
                            try {
//                                System.out.println(Thread.currentThread().getName() + "开始等待");
                                this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print(flag);
//                        记得唤醒
                        this.notifyAll();
                        this.flag = nextFlag;
                    }
                }
        }
    }

    static class SyncLog extends ReentrantLock {
        private int loopNumber;

        public SyncLog(int loopNumber) {
            this.loopNumber = loopNumber;
        }

        public void start(Condition first) {
            this.lock();
            try {

                first.signalAll();
            } finally {
                this.unlock();
            }
        }
        public void print(Condition current, Condition next, char val) {
            for (int i = 0; i < loopNumber; i++) {
                this.lock();
                try {
                    current.await();
                    System.out.print(val);
//                  统一先等待再唤醒
                    next.signal();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    this.unlock();
                }
            }
        }
    }

    static class SyncPark {
        int loopNumber;
        LinkedList<Thread> threadPool = new LinkedList();
        public SyncPark(int loopNumber) {
            this.loopNumber = loopNumber;
        }

        public void start(Thread... threads) {
            for (Thread thread : threads) {
                threadPool.add(thread);
                thread.start();
            }
            LockSupport.unpark(threadPool.get(0));
        }

        public Thread nextThread(int index) {
            if (index == threadPool.size())
                return this.threadPool.get(0);
            return this.threadPool.get(index);
        }

        public void print(int index, int val) {
            for (int i = 0; i < loopNumber; i++) {
                LockSupport.park();
                System.out.print(val);
                LockSupport.unpark(nextThread(index + 1));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SyncWaitNotify syncWaitNotify = new SyncWaitNotify(1, 5);
        SyncLog syncLog = new SyncLog(5);
        SyncPark syncPark = new SyncPark(5);
        final Condition con1 = syncLog.newCondition();
        final Condition con2 = syncLog.newCondition();
        final Condition con3 = syncLog.newCondition();
        final Condition con4 = syncLog.newCondition();
        final Condition con5 = syncLog.newCondition();
        new Thread(() -> {
//            syncWaitNotify.print(1, 2);
            syncLog.print(con1, con2, 'a');
//            syncPark.print(0, 1);
        }, "t1").start();
        new Thread(() -> {
//            syncWaitNotify.print(2, 3);
            syncLog.print(con2, con3, 'b');
//            syncPark.print(1, 2);
        }, "t2").start();
        new Thread(() -> {
//            syncWaitNotify.print(3, 4);
            syncLog.print(con3, con4, 'c');
//            syncPark.print(2, 3);
        }, "t3").start();
        new Thread(() -> {
//            syncWaitNotify.print(4, 5);
            syncLog.print(con4, con5, 'd');
//            syncPark.print(3, 4);
        }, "t4").start();
        new Thread(() -> {
//            syncWaitNotify.print(5, 1);
            syncLog.print(con5, con1, 'e');
//            syncPark.print(4, 5);
        }, "t5").start();
//        syncPark.start(t1, t2, t3, t4, t5);
        syncLog.start(con1);
    }
}
