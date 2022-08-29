import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class demo13 implements Account{
    public demo13() {

    }



    public static void main(String[] args) {

        LinkedList<demo13> ints = new LinkedList();
        for (int i = 0; i < 10; i++) {
            ints.add(new demo13());
        }

        ints.forEach(demo13::prints);
        Account.demo(new demo13(10000));
    }
    public void run() {
        System.out.println("woc");
    }
    public  void prints() {
        System.out.println(1);
    }
    private AtomicInteger balance;

    public demo13(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }
    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        while (true) {
            // 通过原子对象获取之前的值
            int prev = balance.get();
            int next = prev - amount;
            // 先比较prev和当前值，一致了就进入锁进行赋值，不一致就返回false
            // 其实 CAS 的底层是 lock cmpxchg 指令(X86 架构)，在单核 CPU 和多核 CPU 下都能够保证【比较-交 换】的原子性。
            // 这个comareAndSet是原子的
            //在多核状态下，某个核执行到带 lock 的指令时，CPU 会让总线锁住，当这个核把此指令执行完毕，再 开启总线。这个过程中不会被线程的调度机制所打断，保证了多个线程对内存操作的准确性，是原子 的。
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}
interface Account {
    Integer getBalance();

    void withdraw(Integer amount);

    static void demo(Account account) {
        List<Thread> ts = new ArrayList();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();
        System.out.println(account.getBalance() + " cost:" + (end - start) / 1000_000 + "ms");
    }
}

interface test {
    void print(int x);
}