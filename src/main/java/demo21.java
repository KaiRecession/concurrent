/**
 * 读锁可以并发执行，不互斥
 * 读锁和写锁互斥
 * 写锁和写锁互斥
 */

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class demo21 {
    public static void main(String[] args) {
        DataContainer dataContainer = new DataContainer();
        new Thread(() -> {
            try {
                dataContainer.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                dataContainer.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}


@Slf4j
class DataContainer {
    private Object data;
    private ReentrantReadWriteLock rw;
    private ReentrantReadWriteLock.ReadLock r;
    private ReentrantReadWriteLock.WriteLock w;

    public DataContainer() {
        this.rw = new ReentrantReadWriteLock();
        this.r = rw.readLock();
        this.w = rw.writeLock();
    }

    public Object read() throws InterruptedException {
        log.debug("获取读锁");
        r.lock();
        try {
            log.debug("读取");
            Thread.sleep(5000);
            return data;
        } finally {
            log.debug("释放读锁");
            r.unlock();
        }
    }

    public void write() throws InterruptedException {
        log.debug("获取写锁");
        w.lock();
        try {
            log.debug("写入");
            Thread.sleep(5000);
        } finally {
            log.debug("释放写锁");
            w.unlock();
        }
    }
}
