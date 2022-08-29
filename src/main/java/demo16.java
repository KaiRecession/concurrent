/**
 * 同步队列的使用方法以及特性
 */

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;

@Slf4j
public class demo16 {
    public static void main(String[] args) throws InterruptedException {
    SynchronousQueue<Integer> integers = new SynchronousQueue<>();
        new Thread(() -> {
            try {
               log.debug("putting{}", 1);
               integers.put(1);
               log.debug("{}, putted", 1);

                log.debug("putting{}", 2);
                integers.put(2);
                log.debug("{}, putted", 2);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        Thread.sleep(1000);

        new Thread(() -> {
            log.debug("taking {}", 1);
            try {
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();

        Thread.sleep(1000);
        new Thread(() -> {
            log.debug("taking {}", 2);
            try {
                integers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3").start();
    }
}
