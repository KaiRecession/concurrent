import lombok.extern.slf4j.Slf4j;

@Slf4j
public class demo06 {
    public static void main(String[] args) throws InterruptedException {
        final Thread t1 = new Thread(() -> {
            final Thread current = Thread.currentThread();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t1线程运行结束");
        }, "t1");

        t1.setDaemon(true);
//      t1线程该结束还是结束，没结束才会提前结束
        t1.start();
        Thread.sleep(5000);
        log.debug("主线程运行结束");

    }
}
