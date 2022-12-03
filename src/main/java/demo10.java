import lombok.extern.slf4j.Slf4j;

@Slf4j
public class demo10 {
    static boolean run = true;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (run) { // ....
//                System.out.println(run);

            }
        });
        t.start();
        Thread.sleep(1000);
        run = false; // 线程t不会如预想的停下来 }
        System.out.println(1);
    }
}
