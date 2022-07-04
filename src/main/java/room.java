public class room {
    int count = 0;
    public void increment() {
        synchronized (this) {
            count++;
        }
    }
    public synchronized void increment2() {
        count++;
    }
    public synchronized void decrement2() {
        count--;
    }
    public void decrement() {
        synchronized (this) {
            count--;
        }
    }
    public int get() {
        synchronized (this) {
            return count;
        }
    }
}
