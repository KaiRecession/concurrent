public class demo05 {
    public static void main(String[] args) throws InterruptedException {
        GuardedObject guardedObject = new GuardedObject();
        Thread t1 = new Thread(() -> {
            try {
                guardedObject.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        Thread t2 = new Thread(() -> guardedObject.set(new Object()), "t2");
        t1.start();
        Thread.sleep(2000);
        t2.start();
    }
}
