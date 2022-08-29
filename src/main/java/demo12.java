/**
 * double-checked locking
 * 单例懒加载模式，双重检查，只有初始化的时候才加锁，效率很高，没有无脑给所有地方加锁
 * 但是这样双重判断的时候，第一重判断没有在synchronized块里面，可能会造成代码重排，先赋值给静态变量，后执行对象的初始化方法
 * 所以说，指令重排也会发生在synchronized块里面。但是如果synchronized里面将所有共享变量的读写程序都包含了那么即使发生了指令重排，也不会有多线程的交错问题发生问题
 */

public class demo12 {
    // 将构造方法设置为private后,外部程序无法访问此方法去实例化一个对象
    private demo12() {}
    volatile private static demo12 INSTANCE = null;
    public static demo12 getInstance() {
        if (INSTANCE == null) {
            synchronized (demo12.class) {
                if (INSTANCE == null) {
                    // 内部指令使用了好几条，保证指令也不会重排
                    INSTANCE = new demo12();
                }
            }
        }
        return INSTANCE;
    }
}
