/**
 * class Test{
 * public synchronized void test() {
 *      临界区代码
 * }
 * }
 * 等价于
 * class Test{
 *         public void test() {
 *         synchronized(this) {
 *             临界区代码（发生竞态条件）
 *         }
 * } }
 *
 *
 * class Test{
 * public synchronized static void test() {
 *      临界区代码
 * }
 * }
 * 等价于
 * class Test{
 *      public static void test() {
 *          synchronized(Test.class) {
 *              临界区代码
 *           }
 * } }
 *
 *
 */

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
