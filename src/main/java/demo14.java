/**
 * 原子整数
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class demo14 {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);
        // 可以自定义函数，x的位置还可以放入一个变量，方便操作，这就很方便了
        System.out.println(i.accumulateAndGet(-10, (p, x) ->  {
            int s = p * x;
            return s + 10;
        }));

        // 相当于i++，++i之类的
        System.out.println(i.getAndIncrement());
        System.out.println(i.incrementAndGet());
        System.out.println(i.decrementAndGet());
        System.out.println(i.getAndDecrement());
        // 相当于i += 5，参数里面甚至可以填负数
        System.out.println(i.getAndAdd(5));
        // 能传入一个函数式接口，p就是prev，里面的函数体保证原子性
        System.out.println(i.updateAndGet(p -> p + 2));

        ReentrantLock lock = new ReentrantLock();
        Condition con1 = lock.newCondition();
        Condition con2 = lock.newCondition();

        Thread t1 = new Thread(() -> {
            lock.lock();
            con2.signal();
            System.out.println("con2空唤醒");
        }, "t1");
        t1.start();
    }
}
