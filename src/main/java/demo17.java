/**
 * Executors的使用
 * execute方法的使用
 */

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class demo17 {
    public static void main(String[] args) {
        test2();
    }
    public static void test2() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(() -> {
            log.debug("1");
            int i = 1 / 0;
        });

        pool.execute(() -> {
            log.debug("2");

        });


        pool.execute(() -> {
            log.debug("3");
        });
    }
}
