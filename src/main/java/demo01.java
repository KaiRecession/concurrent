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
        Runnable runnable = () -> log.debug("woc");
        Thread t2 = new Thread(runnable, "t2");
        final Thread t3 = new Thread(() -> {
            log.debug("woc");
        }, "t3");
        t1.start();
        t2.start();
        t3.start();
        A a = (o1, o2) -> o1 - o2;
    }
}

interface A {
    int test(int a, int b);
}
