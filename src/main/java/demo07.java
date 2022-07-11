import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "parkDemo")
public class demo07 {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            log.debug("start......");
            try {
                synchronized (lock) {
                    lock.wait(1);
                }
                Thread.sleep(2000);
                log.debug("park");
                LockSupport.park();
                log.debug("resume........");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        Thread.sleep(1000);
//        getstate状态比idea调试器中的状态更加详细
        System.out.println(t1.getState());
        log.debug("unpark");
//        可以先unpark
        LockSupport.unpark(t1);
    }
}
