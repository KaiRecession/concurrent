import lombok.extern.slf4j.Slf4j;
/*
 synchronized(对象) // 线程1， 线程2(blocked)
 {
   临界区
}
 */
@Slf4j
public class demo04 {
    static int count = 0;
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        final room room = new room();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (room) {
                    synchronized (room) {
                    // 锁的重入
                        count++;
                    }
                }
                room.increment();
                room.increment2();
            }

        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (room) {
                    count--;
                }
                room.decrement();
                room.decrement2();
            }

        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count:{}", count);
        log.debug("count: {}", room.get());
    }
}
