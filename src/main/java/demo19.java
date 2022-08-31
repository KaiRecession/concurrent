/**
 * 测试线程调度
 */

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class demo19 {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.schedule(() -> {
            System.out.println("任务1， 执行时间： " + new Date());
        }, 1000, TimeUnit.MILLISECONDS);

        executor.schedule(() -> {
            System.out.println("任务2， 执行时间： " + new Date());
        }, 1000, TimeUnit.MILLISECONDS);

        ScheduledExecutorService pool =  Executors.newScheduledThreadPool(1);
        log.debug("start...");
        pool.scheduleAtFixedRate(() -> {
            log.debug("running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, 1, 1, TimeUnit.SECONDS);
    }
}
