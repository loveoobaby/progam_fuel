## 1.  jvm基本结构

+ 类加载子系统：负责从文件系统或网络中加载Class信息并存放在方法区；

+ heap：几乎所有的对象实例存放在heap中，为所有线程共享；

+ 直接内存：NIO库可以直接使用堆外内存，通常访问速度优于堆，但分配速度慢，适用于一次分配多次读取的场景；

+ 垃圾回收系统：对方法区、heap、直接内存进行回收；

+ java栈：每一个线程都有一个私有的栈；

+ 本地方法栈：与java栈类似，但用于本地方法执行；

+ PC寄存器：线程私有空间，每个线程都有一个，指向当前正在被执行的指令，如果正在执行本地方法则为undefined

+ 执行引擎：负责执行虚拟机的字节码；

  ![](./picture/jvm结构.png)

### 1.2. 什么是堆？

根据垃圾回收机制的不同，堆有不同的结构，最常见的是将堆分成老年代和新生代。

![](./picture/jvm_heap.png)

### 1.3. java栈

+    程执行的基本行为是函数调用，每一次函数调用，都会有一个对应的栈帧入栈，函数调用结束栈帧弹出。当前正在执行的函数对应的帧位于栈顶，保存着当前函数的局部变量、中间运算结果等数据；一个栈帧中至少包含局部变量表、操作数栈和帧数据区；
+ -Xss指定线程的最大栈空间；
+ 局部变量表：用于保存函数的参数以及局部变量。可以使用jclasslib查看。
+ 操作数栈：用于保存计算过程的中间结果，同时作为计算过程中变量的临时存储空间，也是一个先进后出的数据结构；；
+ 帧数据区：保存着访问常量池的指针，方便访问常量池；同时保存着异常处理表，方便在发生异常的时候找到处理异常的代码；
+ 栈上分配：jvm提供的一项优化技术，基本思路是对于那些线程私有的对象，可以将他们打散分配到栈上，而不是分配到堆上，这样当函数调用结束后可以自行销毁，不需要垃圾回收器介入，从而提高系统性能。逃逸分析是该技术的基础。栈上分配的例子：

```Java
public class TestEscape {

    public static class User{
        public int id;
        public String name;

    }

    public static void main(String[] args) {
        long b = System.currentTimeMillis();
        for (int i = 0; i < 1e8; i++) {
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println(e-b);
    }

    private static void alloc() {
        User u = new User();
        u.id = 2;
        u.name = "yss";
    }

}
jvm运行参数：
-server -Xmx10m -Xms10m -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -XX:-UseTLAB -XX:+EliminateAllocations
```

### 1.4. 方法区

+ 线程共享区域，用于保存类信息。在1.7中，方法区可以理解为永久区，在1.8中永久区被移除，取而代之的是元数据区，元数据区的大小由-XX:MaxMetaspaceSize指定。如果元数据区发生溢出，虚拟机一样会抛出OOM

  异常。



## 2. 常用JVM参数

### 2.1 垃圾回收日志打印

+ -XX:+PrintGC,  打印GC简略日志，类似如下：

`[GC 345536K->790K(1048064K), 0.0003551 secs]`

在GC前，堆空间使用量为345M，GC后，堆空间使用量为790K，可用堆空间是1048M；

+ -XX:+PrintGCDetails可以输出更详细的信息：

  [GC  [PSYoungGen: 53205K->256K(64512K)] 65846K->12968K(201216K), 0.0014672 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 

  [GC  [PSYoungGen: 42476K->224K(64512K)] 55188K->23232K(201216K), 0.0073756 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 

  [Full GC  [PSYoungGen: 224K->0K(64512K)] [ParOldGen: 23008K->12644K(136704K)] 23232K->12644K(201216K), [Metaspace: 9212K->9212K(1058816K)], 0.0135689 secs] [Times: user=0.07 sys=0.00, real=0.01 secs] 

   前两条是YoungGc日志，最后一条是Full GC，新生代从224K变为0K，整个可用新生代大小为64512K，老年代从23008K变为12644K，整个可用老年代大小为136704K，整个堆从23232K变为12644K，整个堆大小为201216K，元空间没有垃圾回收，大小为1058816K；

  在jvm退出时，还会打印堆的详细信息：

   PSYoungGen      total 65536K, used 53866K [0x00000007bbd80000, 0x00000007c0000000, 0x00000007c0000000)
    eden space 62976K, 85% used [0x00000007bbd80000,0x00000007bf1f2ab0,0x00000007bfb00000)
    from space 2560K, 6% used [0x00000007bfd80000,0x00000007bfda8000,0x00000007c0000000)
    to   space 2560K, 0% used [0x00000007bfb00000,0x00000007bfb00000,0x00000007bfd80000)

  

   ParOldGen       total 136704K, used 13180K [0x00000007b3800000, 0x00000007bbd80000, 0x00000007bbd80000)

    object space 136704K, 9% used [0x00000007b3800000,0x00000007b44df1b8,0x00000007bbd80000)
   Metaspace       used 9440K, capacity 9874K, committed 9984K, reserved 1058816K
    class space    used 1109K, capacity 1224K, committed 1280K, reserved 1048576K

  末尾的三个十六进制代表各区域的堆空间下界、当前上界、上界；

  如：（0x00000007c0000000 - 0x00000007bbd80000）/ 1024 = 68096K = eden space + from space + to space

+ -XX:PrintHeapAtGC: 可以在每次GC前后分别打印堆信息

  {Heap before GC invocations=2 (full 0):
   PSYoungGen      total 59904K, used 43148K [0x00000007bbd80000, 0x00000007c0000000, 0x00000007c0000000)
    eden space 51712K, 81% used [0x00000007bbd80000,0x00000007be67a008,0x00000007bf000000)
    from space 8192K, 14% used [0x00000007bf000000,0x00000007bf129300,0x00000007bf800000)
    to   space 8192K, 0% used [0x00000007bf800000,0x00000007bf800000,0x00000007c0000000)
   ParOldGen       total 136704K, used 8K [0x00000007b3800000, 0x00000007bbd80000, 0x00000007bbd80000)
    object space 136704K, 0% used [0x00000007b3800000,0x00000007b3802000,0x00000007bbd80000)
   Metaspace       used 3349K, capacity 4496K, committed 4864K, reserved 1056768K
    class space    used 366K, capacity 388K, committed 512K, reserved 1048576K
  Heap after GC invocations=2 (full 0):
   PSYoungGen      total 59904K, used 928K [0x00000007bbd80000, 0x00000007c0000000, 0x00000007c0000000)
    eden space 51712K, 0% used [0x00000007bbd80000,0x00000007bbd80000,0x00000007bf000000)
    from space 8192K, 11% used [0x00000007bf800000,0x00000007bf8e8000,0x00000007c0000000)
    to   space 8192K, 0% used [0x00000007bf000000,0x00000007bf000000,0x00000007bf800000)
   ParOldGen       total 136704K, used 16K [0x00000007b3800000, 0x00000007bbd80000, 0x00000007bbd80000)
    object space 136704K, 0% used [0x00000007b3800000,0x00000007b3804000,0x00000007bbd80000)
   Metaspace       used 3349K, capacity 4496K, committed 4864K, reserved 1056768K
    class space    used 366K, capacity 388K, committed 512K, reserved 1048576K
  }

### 2.2 类加载卸载跟踪

- -verbose:class   可以跟踪类的加载和卸载

  `[Opened /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar]
  [Loaded java.lang.Object from /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar]
  [Loaded java.io.Serializable from /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar]
  [Loaded java.lang.Comparable from /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar]`

- -XX:+TraceClassLoading： 可以单独跟踪类的加载

- -XX:+TraceClassUnloading: 单独跟踪类的卸载

### 2.3  打印虚拟机参数

+ -XX:+PrintVMOptions:打印虚拟机的接受的显式参数
+ -XX:+PrintCommandLineFlags: 打印虚拟机的显式和隐式参数；

### 2.4 堆参数

+ 最大堆与最小堆设置：-Xmx -Xms
+ -Xmn可以设置新生代的大小。设置较大的新生代会减少老年代的大小。新生代大小一般设置为整个堆的1/3到1/4;
+ -XX:SurvivorRatio=eden/from: 设置新生代中的eden与from/to空间的比例

​        如下面的参数： -Xmn2m -XX:SurvivorRatio=2  设置了新生代共2048K，eden空间为1024K，from/to为512K，可用新生代就是1024K+512K=1536K；

+ 设置老年代与新生代的比例：-XX:+NewRatio=老年代/新生代
+ -XX:+HeapDumpOnOutOfMemoryError: 设置在堆溢出时，导出整个堆信息；
+ -XX:HeapDumpPath=dump file path: 设置导出堆文件的存放路径；

### 2.5 堆外内存设置

+ -XX:MaxMetaspaceSize:指定元数据区大小
+ -XX:MaxDirectMemorySize: 指定最大直接内存，如果不设置默认是最大堆空间即-Xmx的值



## 3 垃圾回收算法

### 3.1 引用计数算法

对于任意一个对象A，只要有任意一个对象引用了A，则A的引用计数器就加1，当引用失效时，引用计数减1。只要引用计数为0就可回收。

面临的问题：

1. 循环引用
2. 引用计数器要求在每次引用产生和消除的时候，伴随这加减操作，对系统性能有一定影响

### 3.2 标记清除算法

标记清除算法是现代垃圾回收算法的思想基础。标记清除算法将垃圾回收分为两个阶段：标记阶段和清除阶段。在标记阶段，从根节点出发，标记所有可达对象。未被标记的对象就是垃圾对象。然后在清除阶段，清除所有未标记对象。这种算法最大的问题是产生空间碎片。

![](./picture/标记清除算法.png)

### 3.3 复制算法

复制算法的核心思想是：将原有的内存空间分成两块，每次只使用其中的一块，在垃圾回收时，将正在使用的内存区域的存活对象复制到未使用的内存区，之后清除正在使用的内存块，最后交换两个内存区域的角色。

![](./picture/复制算法.png)









































