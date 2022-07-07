public class demo05 {
    public static void main(String[] args) throws InterruptedException {
        GuardedObject guardedObject = new GuardedObject();
        Thread t1 = new Thread(() -> {
            try {
                guardedObject.get();
                guardedObject.get2();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            guardedObject.set(new Object());
            guardedObject.set2(new Object());
            }, "t2");
        Thread t3 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3");
        t1.start();
        t3.start();
        Thread.sleep(2000);
        t2.start();
    }
}
