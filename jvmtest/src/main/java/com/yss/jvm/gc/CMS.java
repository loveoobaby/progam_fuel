package com.yss.jvm.gc;

/*
   1. CMS(ConCurrent Mark Sweep)收集器，以获取最短回收停顿时间为目标，多数用户互联网站或B/S系统的服务器上
   2. CMS基于"标记-清除"算法实现，整个过程分为4个步骤：
       1. 初始标记 （initial mark）
       2. 并发标记 （Concurrent mark）
       3. 重新标记 （Remark）
       4. 并发清除（Current Sweep）
   3. 初始标记、重新标记这两个步骤仍然需要Stop the world
   4. 初始标记只是标记一下GC Roots能直接关联到的对象，速度很快；
      并发标记是进行GC Roots Tracing的过程
      重新标记阶段是为了修正并发标记期间因用户线程继续运作而导致标记产生变动的那一部分对象的标记记录
   5. CMS收集器运作的整个过程中耗时最长的是并发标记与并发清除过程收集器线程都可以与用户线程一起工作，因此，
      从总体来看，CMS收集器的内存回收过程是与用户线程一起并发执行的。
   6. 优点：通过将大量工作分散到并发处理阶段来减少STW时间。并发收集、低停顿，
      缺点：1. CMS对CPU资源非常敏感
           2. CMS收集器无法处理浮动垃圾，可能出现"Concurrent Mode Failure"失败而导致另一次Full GC的产生。
           如果在应用中老年代增长不是太快，可以适当调高参数-XX:CMSInitiatingOccupancyFraction的值来提高
           触发百分比，以便降低内存回收次数从而获取更好的性能。要是CMS运行期间预留的内存无法满足程序的需要时，
           虚拟机将启动后备预案：临时启动Serial Old收集器来重新进行老年代的垃圾收集，这样停顿时间就很长了。所以
           参数 -XX:CMSInitiatingOccupancyFraction设置的太高很容易导致大量"Concurrent Mode Failure"，性能反而降低
           3. 收集结束时会有大量的空间碎片产生，空间碎片过多时，将会给大对象的分配带来很大麻烦，往往老年代还有很大的空间
           剩余，但是无法找到足够大的连续空间来分配当前对象，不得不提前进行一次FullGC。CMS收集器提供一个-XX:+UseCMSCompactAtFullCollectionkaiguan
           参数（默认开启），用于在CMS收集器顶不住进行FullGC时开启内存碎片的合并整理过程，内存整理的过程无法并发的，空间碎片没有了，但
           停顿时间不得不变长。
           4. 对于堆较大的一些应用，GC的时间难以预估

   7. CMS收集器步骤：
       1. Initial Mark：标记那些直接被GC Root引用或被年轻代存活对象引用的老年代对象
       2. Concurrent Mark：遍历老年代，然后标记所有存活对象
       3. Concurrent Preclean：
       4. Concurrent Abortable Preclean
       5. Final Remark
       6. Concurrent Sweep
       7. Concurrent Reset：重设CMS内部的数据结构，为下次的GC做准备。






















 */
public class CMS {
}
