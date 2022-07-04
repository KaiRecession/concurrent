import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuardedObject {
    final Object lock = new Object();
    Object response = null;

    public Object get() throws InterruptedException {
        synchronized (lock) {
            while (response == null) {
                log.debug(Thread.currentThread().getName());
                log.debug("等待");
                lock.wait();
            }
            log.debug("已经被唤醒");
            return response;
        }
    }

    public void set(Object response) {
        synchronized (lock) {
            this.response = response;
            log.debug("唤醒");
            lock.notifyAll();
        }
    }
}
