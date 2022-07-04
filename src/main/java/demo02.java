import lombok.extern.slf4j.Slf4j;

@Slf4j
public class demo02 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {

            final Thread thread = Thread.currentThread();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("t1的打断状体啊{}", thread.isInterrupted());
            }
            log.debug("t1的打断状体啊{}", thread.isInterrupted());
           log.debug("woc");

        }, "t1");
        Thread t2 = new Thread(() -> {
            while(true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
                if (interrupted) {
                    log.debug("t2打断状态：{}", interrupted);
                    break;
                }
            }
        }, "t2");
        t1.start();
        t2.start();
        Thread.sleep(500);
        t1.interrupt();
       // t2.interrupt();
        log.debug("t1打断状态： {}", t1.isInterrupted());
        log.debug("t1的打断状体啊{}", t1.isInterrupted());
    }

    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        Thread.sleep(500);
        t1.interrupt();
        log.debug(" 打断状态: {}", t1.isInterrupted());
    }
}
