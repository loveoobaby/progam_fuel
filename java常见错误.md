### 1. 数组转list

```java
Double[] data = new Double[]{null, 1., 2., 3., 4.};

List<Double> list = Arrays.asList(data);
System.out.println(list.getClass());
list.add(5.);
```

Arrays.asList()将返回一个数组内部是私有静态类的ArrayList，这不是java.util.ArrayList类。它没有实现add方法，他的大小是固定的，所以上述代码输出：

```java
class java.util.Arrays$ArrayList
Exception in thread "main" java.lang.UnsupportedOperationException
	at java.util.AbstractList.add(AbstractList.java:148)
	at java.util.AbstractList.add(AbstractList.java:108)
	at com.yss.henghe.iris.TestError.main(TestError.java:20)
```

如果需要创建大小动态变化的list，可以用一下方式：

```java
ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(arr));
```



### 2. 在循环遍历列表中删除元素

```java
ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
for (int i = 0; i < list.size(); i++) {
	list.remove(i);
}
System.out.println(list);
```

上述代码输出：[b,d]， 并没有达到全部删除元素的目的。原因是当一个元素被删除，列表的size已经改变，指示标记i已经改变指向。

正确的做法是改用迭代器，但不能使用foreach循环：

```java
ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));

for (String s : list) {
    if (s.equals("a"))
        list.remove(s);
}
```

上述代码会抛出ConcurrentModificationException异常。下面才是正确的代码：

```java
ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
Iterator<String> iter = list.iterator();
while (iter.hasNext()) {
	String s = iter.next();
 
	if (s.equals("a")) {
		iter.remove();
	}
}
```

next()必须在remove之前被调用。在foreach循环中，编译器将删除元素后调用next()方法。

### 3. 容器使用原始类型而不是参数化

```java
List listOfNumbers = new ArrayList();  
listOfNumbers.add(10);  
listOfNumbers.add("Twenty");  
listOfNumbers.forEach(n -> System.out.println((int) n * 2)); 
```

此代码编译时不会出错，但是一旦运行就会抛出运行时错误，因为这里试图将字符类型映射为整形。很显然，如果隐藏了必要信息，类型系统将不能帮助写出[安全](http://blog.oneapm.com/tags-anquan.html)代码。

### 4. 未使用 Buffer 进行 I/O 操作

除 NIO 外，使用 Java 进行 I/O 操作有两种基本方式：

- 使用基于 InputStream 和 OutputStream 的方式；
- 使用 Writer 和 Reader。

无论使用哪种方式进行文件 I/O，如果能合理地使用缓冲，就能有效地提高 I/O 的性能。

下面显示了可与 InputStream、OutputStream、Writer 和 Reader 配套使用的缓冲组件。

OutputStream-FileOutputStream-BufferedOutputStream

InputStream-FileInputStream-BufferedInputStream

Writer-FileWriter-BufferedWriter

Reader-FileReader-BufferedReader

```java
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class StreamVSBuffer {
    public static void streamMethod() throws IOException {
        try {
            long start = System.currentTimeMillis();
            DataOutputStream dos = new DataOutputStream(
                    new FileOutputStream("/tmp/StreamVSBuffertest1.txt"));
            for (int i = 0; i < 1000000; i++) {
                dos.writeBytes(String.valueOf(i) + "\r\n");//循环 1 万次写入数据
            }
            dos.close();
            DataInputStream dis = new DataInputStream(new FileInputStream("/tmp/StreamVSBuffertest1.txt"));
            while (dis.readLine() != null) {

            }
            dis.close();
            System.out.println(System.currentTimeMillis() - start);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void bufferMethod() throws IOException {
        try {
            long start = System.currentTimeMillis();
            //请替换成自己的文件
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream("/tmp/StreamVSBuffertest.txt")));
            for (int i = 0; i < 1000000; i++) {
                dos.writeBytes(String.valueOf(i) + "\r\n");//循环 1 万次写入数据
            }
            dos.close();
            DataInputStream dis = new DataInputStream(new BufferedInputStream(
                    new FileInputStream("/tmp/StreamVSBuffertest.txt")));
            while (dis.readLine() != null) {

            }
            dis.close();
            System.out.println(System.currentTimeMillis() - start);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            StreamVSBuffer.streamMethod();
            StreamVSBuffer.bufferMethod();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

运行输出：

```
50529
205
```

FileWriter 和 FileReader 代码：

```java
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class WriterVSBuffer {
    public static void streamMethod() throws IOException{
        try {
            long start = System.currentTimeMillis();
            FileWriter fw = new FileWriter("/tmp/Buffertest1.txt");//请替换成自己的文件
            for(int i=0;i<10000;i++){
                fw.write(String.valueOf(i)+"\r\n");//循环 1 万次写入数据
            }
            fw.close();
            FileReader fr = new FileReader("/tmp/Buffertest1.txt");
            while(fr.read() > 0){

            }
            fr.close();
            System.out.println(System.currentTimeMillis() - start);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void bufferMethod() throws IOException{
        try {
            long start = System.currentTimeMillis();
            BufferedWriter fw = new BufferedWriter(new FileWriter("/tmp/Buffertest2.txt"));//请替换成自己的文件
            for(int i=0;i<10000;i++){
                fw.write(String.valueOf(i)+"\r\n");//循环 1 万次写入数据
            }
            fw.close();
            BufferedReader fr = new BufferedReader(new FileReader("/tmp/Buffertest2.txt"));
            while(fr.readLine() != null){
//                System.out.println(111);
            }
            fr.close();
            System.out.println(System.currentTimeMillis() - start);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        try {
            WriterVSBuffer.streamMethod();
            WriterVSBuffer.bufferMethod();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

输出：

```java
47
8
```

