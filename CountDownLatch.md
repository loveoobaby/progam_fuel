## 1. CountDownLatch的使用

​	CountDownLatch的作用是一个计数器，每个线程执行完毕后会执行countDown，表示自己已结束，这对于多个子任务的计算特别有效。例如一个任务可以分成10个子任务执行，主线程必须要知道子线程任务是否已完成，所有子任务完成后，主线程进行合并计算。

```java
public class TestCountDownLatch {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int num = 10;
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(num);

        ExecutorService es = Executors.newFixedThreadPool(num);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            futures.add(es.submit(new Runner(begin, end)));
        }
        begin.countDown();
        end.await();

        int count = 0;
        for (Future<Integer> f: futures) {
            count += f.get();
        }
        System.out.println("平均分数为：" + count / num);
        es.shutdown();

    }



}

class Runner implements Callable<Integer> {

    private CountDownLatch begin;
    private CountDownLatch end;

    public Runner(CountDownLatch begin, CountDownLatch end){
        this.begin = begin;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int score = new Random().nextInt(25);
        begin.await();
        TimeUnit.SECONDS.sleep(score);
        end.countDown();
        return score;
    }
}
```

## 2. CountDownLatch的源码

### 2.1 构造函数

```java
public CountDownLatch(int count) {
    if (count < 0) throw new IllegalArgumentException("count < 0");
    this.sync = new Sync(count); // 初始化状态变量
}
```

其中Sync是AQS的子类，构造函数如下：

```java
Sync(int count) {
    setState(count);
}
```

设置状态变量state，其中state是个volatile 用于保证可见性；























