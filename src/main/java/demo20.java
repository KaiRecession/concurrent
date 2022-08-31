/**
 * 跟递归函数一样，可以用二分的方法进行优化
 */

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class demo20 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new AddTask1(5)));
    }
}

@Slf4j
class AddTask1 extends RecursiveTask<Integer> {
    int n;

    // 相当于递归函数的本体了
    public AddTask1(int n) {
        this.n = n;
    }

    public String toString() {
        return "{" + n + "}";
    }

    @Override
    protected Integer compute() {

        // 基准条件
        if (n == 1) {
            log.debug("join() {}", n);
            return n;
        }

        // 开始递归
        AddTask1 t1 = new AddTask1(n - 1);
        t1.fork();
        log.debug("fork() {} + {}", n, t1);

        int result = n + t1.join();
        log.debug("join() {} + {} = {}", n, t1, result);
        return result;
    }

}
