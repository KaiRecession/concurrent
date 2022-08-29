import java.util.concurrent.atomic.AtomicInteger;

public class demo11 {

        static int num = 0;
        static boolean ready = false;
    public static void main(String[] args) throws InterruptedException {
        I_Result r = new I_Result();
        Thread t1 = new Thread(() -> {
            if (ready) {
//                num = 1;
                r.r1 = num + num;
            } else {
                r.r1 = 1;
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
//            如果不发生指令重排，那么ready为True时，num肯定为2，所以r1永远不可能为0，但是指令重排后就有可能
            num = 2;
            ready = true;
        }, "t2");
        t1.start();
        t2.start();

        System.out.println(r.r1);
        AtomicInteger test = new AtomicInteger(0);
    }
}

class I_Result {
    int r1 = -1;
}
