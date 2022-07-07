import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuardedObject {
    final Object lock = new Object();
    Object response = null;
    Object response2 = null;

    public Object get() throws InterruptedException {
        synchronized (lock) {
            while (response == null) {
                log.debug(Thread.currentThread().getName());
                log.debug("等待");
                lock.wait();
//              线程的状态是wait状态
                log.debug("wait后的状态");
            }
            log.debug("已经被唤醒");
            return response;
        }
    }

    // 保护性暂停的精髓
    public synchronized Object get2() throws InterruptedException {
        while (response2 == null) {
            this.wait();
        }
        return response2;
    }

    public synchronized void set2(Object response2) {
        this.response2 = response2;
        this.notifyAll();
    }

    public void set(Object response) {
        synchronized (lock) {
            this.response = response;
            log.debug("唤醒");
            lock.notifyAll();
        }
    }
}
