public class volatileTest {
    static int a = 1;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(a);
            a = 2;
            System.out.println(a);
        }, "t1").start();
        new Thread(() -> {
            System.out.println(a);
            a = 3;
            System.out.println(a);
        }, "t2").start();
    }
}
