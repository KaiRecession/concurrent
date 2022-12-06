import java.util.ArrayList;

public class ThreadSafe {
    public final void method1(int loopNumber) {
        ArrayList<String> list = new ArrayList<>(); for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list); }
    }
    private void method2(ArrayList<String> list) {
        list.add("1");
    }
    public void method3(ArrayList<String> list) {
        list.remove(0);
    }
    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 2000;
    public static void main(String[] args) {
        ThreadSafe test = new ThreadSafe();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "Thread" + i).start();
        }
    }
}

// 让list暴露在线程外就会有线程安全问题，所以设置为private，不让子类继承
class ThreadSafeSubClass extends ThreadSafe{
    @Override
    public void method3(ArrayList<String> list) {
        new Thread(() -> {
    list.remove(0); }).start();
} }
