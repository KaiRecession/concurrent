import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class demo08 {
    static ReentrantLock lock = new ReentrantLock();
    static volatile boolean has1 = false;
    static volatile boolean has2 = false;
    public static void main(String[] args) throws InterruptedException {
        Condition con1 = lock.newCondition();
        Condition con2 = lock.newCondition();

        method1();
        Thread t1 = new Thread(() -> {
            log.debug("线程启动");
            try {
                System.out.println(222222);
                lock.lockInterruptibly(); // 等待锁的时候可以被打断
                System.out.println(333333);
            } catch (InterruptedException e){
                e.printStackTrace();
                log.debug("等锁被打断");
                return;
            }
            System.out.println(11111111);
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock(); // 放在finally是为了保证一定会释放锁，锁和try、finally没有必然联系
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            log.debug("启动、、、、");
            if (!lock.tryLock()) {
                log.debug("获取锁失败，直接推出");
                return;
            }
            try {
                log.debug("获得了锁（不可能）");

            } finally {
                lock.unlock();
            }
        }, "t2");
        Thread t3 = new Thread(() -> {
            log.debug("启动....");
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("获取等待1s后失败");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t3");
        Thread t4 = new Thread(() -> {
            lock.lock();
            try {
                while (!has1) {
                    try {
                        con1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("has1此时为true");
            } finally {
                System.out.println("释放锁。。。。。。");
                // 释放锁是必要的
                lock.unlock();
            }
        }, "t4");
        Thread t5 = new Thread(() -> {
            lock.lock();
            while (!has2) {
                try {
                    System.out.println("con2进入等待");
                    con2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                log.debug("has2此时为true");
            }
        }, "t5");
        // 主线程获得锁
        lock.lock();
        log.debug("获得了锁");
        t1.start();
        t2.start();
        t3.start();
        try {
            Thread.sleep(3000);
            t1.interrupt();
            log.debug("执行中断");
        } finally {
            lock.unlock();
        }

        t4.start();
        t5.start();
        Thread.sleep(1000);
        lock.lock();
        try {
            log.debug("将has1设置为true");
            has1 = true;
            con1.signalAll();
        } finally {
            lock.unlock();
        }

        Thread.sleep(2000);
        lock.lock();
        try {
            log.debug("将has2设置为true");
            has2 = true;
//          使用唤醒方法时，要在同步块里面！！！
            con2.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public static void method1() {
        lock.lock();
        try {
            log.debug("执行方法1");
            method2();
        } finally {
            lock.unlock();
        }
    }

    public static void method2() {
        lock.lock();
        try {
            log.debug("执行方法2");
            method3();
        } finally {
            lock.unlock();
        }
    }

    public static void method3() {
        lock.lock();
        try {
            log.debug("执行方法3");
        } finally {
            lock.unlock();
        }
    }
}
