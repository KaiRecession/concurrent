import lombok.extern.slf4j.Slf4j;

@Slf4j
public class demo03 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Thread current = Thread.currentThread();
            while(true) {
                if (current.isInterrupted()) {
                    log.debug("准备动手");
                    break;
                }
                try {
                    Thread.sleep(2000);
                    log.debug("正在运行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    current.interrupt();
                }

            }
        });
        t1.start();
        Thread.sleep(8000);
        t1.interrupt();
    }
}
