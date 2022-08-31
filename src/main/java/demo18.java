/**
 * 测试submit的任务提交方法
 * 测试future的对象使用
 */

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class demo18 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        method1(pool);

    }

    public static void method1(ExecutorService pool) throws ExecutionException, InterruptedException {
        Future<String> future =  pool.submit(() -> {
            log.debug("running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("return");
            return "OK";
        });

        log.debug("{}", future.get());
        System.out.println("wait finished");
    }
}
