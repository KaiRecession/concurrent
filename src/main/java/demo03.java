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
//                   里面也可加其他的代码，sleep可以保证该线程不会占用太多的cpu，所以sleep代码和catch异常是一定要有的
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


class prac {
    public void test() {
        new Thread(() -> {
            final Thread thread = Thread.currentThread();
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    thread.interrupt();
                }
                if (thread.isInterrupted()) {
                    System.out.println("我被打断了");
                    break;
                }
            }
        }, "t3");
    }
}
