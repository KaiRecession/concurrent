import lombok.extern.slf4j.Slf4j;

@Slf4j
public class demo01 {
    public static void main(String[] args) {

        log.debug("woc");
        Thread t1 = new Thread("t1") {
            public void run() {
                log.debug("woc");
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.debug("woc");
            }
        };
        Thread t2 = new Thread(runnable, "t2");
        final Thread t3 = new Thread(() -> {
            log.debug("woc");
        }, "t3");
        t1.start();
        t2.start();
        t3.start();
    }
}
