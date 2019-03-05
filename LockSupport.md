### 1. LockSupport字段

LockSupport是JUC并发支持类，提供锁机制与线程的阻塞唤醒的工具类。

先看其字段部分：

```java
// UNSAFE实例
private static final sun.misc.Unsafe UNSAFE; 
// Thread类中parkBlocker字段内存偏移地址
private static final long parkBlockerOffset;
// threadLocalRandomSeed字段偏移地址
private static final long SEED;
// threadLocalRandomProbe字段偏移地址
private static final long PROBE;
// threadLocalRandomSecondarySeed字段偏移地址
private static final long SECONDARY;
static {
    try {
        UNSAFE = sun.misc.Unsafe.getUnsafe();
        Class<?> tk = Thread.class;
        parkBlockerOffset = UNSAFE.objectFieldOffset
            (tk.getDeclaredField("parkBlocker"));
        SEED = UNSAFE.objectFieldOffset
            (tk.getDeclaredField("threadLocalRandomSeed"));
        PROBE = UNSAFE.objectFieldOffset
            (tk.getDeclaredField("threadLocalRandomProbe"));
        SECONDARY = UNSAFE.objectFieldOffset
            (tk.getDeclaredField("threadLocalRandomSecondarySeed"));
    } catch (Exception ex) { throw new Error(ex); }
}
```

其构造方法是私有的，不能实例化：

```java
private LockSupport() {} // Cannot be instantiated.
```

### 2. 核心函数

在分析LockSupport函数之前，先引入sun.misc.Unsafe类中的park和unpark函数，因为LockSupport的核心函数都是基于Unsafe类中定义的park和unpark函数，下面给出两个函数的定义。

```java
public native void park(boolean isAbsolute, long time);
public native void unpark(Thread thread);
```

这两个函数说明如下：

1. park函数，阻塞线程，并且该线程在下列情况发生之前都会被阻塞：① 调用unpark函数，释放该线程的许可。② 该线程被中断。③ 设置的时间到了。并且，当time为绝对时间时，isAbsolute为true，否则，isAbsolute为false。当time为0时，表示无限等待，直到unpark发生。
2. unpark函数，释放线程的许可，即激活调用park后阻塞的线程。这个函数不是安全的，调用这个函数时要确保线程依旧存活。



LockSupport的park函数：

```java
public static void park() {
    UNSAFE.park(false, 0L);
}

public static void park(Object blocker) {
     Thread t = Thread.currentThread();
     setBlocker(t, blocker);
     UNSAFE.park(false, 0L);
     setBlocker(t, null);
}
```

两个函数的区别在于park()函数没有没有blocker，即没有设置线程的parkBlocker字段。该字段可以在阻塞时被记录，以允许监视工具和诊断工具确定线程受阻塞的原因。



**parkNanos函数**：阻塞当前线程，并最多等待指定的等待时间。

```java
public static void parkNanos(Object blocker, long nanos) {
    if (nanos > 0) {
        Thread t = Thread.currentThread();
        setBlocker(t, blocker);
        UNSAFE.park(false, nanos);
        setBlocker(t, null);
    }
}
```



**parkUntil函数**：指定的时限前禁用当前线程，除非被其他线程调用unpark唤醒

```java
public static void parkUntil(long deadline) {
    UNSAFE.park(true, deadline);
}
```



**unpark**函数：唤醒一个线程。

```java
public static void unpark(Thread thread) {
    if (thread != null)
        UNSAFE.unpark(thread);
}
```



### 3. 使用LockSupport的例子



```java

import java.util.concurrent.locks.LockSupport;

class MyThread extends Thread {
    private Object object;

    public MyThread(Object object) {
        this.object = object;
    }

    public void run() {
        System.out.println("before unpark");        
        // 释放许可
        LockSupport.unpark((Thread) object);
        System.out.println("after unpark");
    }
}

public class ParkAndUnparkDemo {
    public static void main(String[] args) {
        MyThread myThread = new MyThread(Thread.currentThread());
        myThread.start();
        try {
            // 主线程睡眠3s
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("before park");
        // 获取许可
        LockSupport.park("ParkAndUnparkDemo");
        System.out.println("after park");
    }
}
```

输出：

```java
MyThread before unpark
MyThread after unpark
main before park
main after park
```



**在先调用unpark，再调用park时，仍能够正确实现同步**，不会造成由wait/notify调用顺序不当所引起的阻塞。因此park/unpark相比wait/notify更加的灵活。



















