# Java内存

## JVM内存区域/逻辑内存模型
1. 程序计数器：线程私有。不会导致OOO
2. Java栈：线程私有。栈深度溢出SOE，栈的动态扩展OOO, -Xss
4. 本地方法栈：为本地方法服务
3. Java堆：线程共享。垃圾回收的主要对象。堆的动态扩展，-Xms, -Xmx, 内存溢出，内存泄露
5. 方法区：线程共享。垃圾回收的次要对象。包括运行时常量池。-XX:PermSize, -XX:MaxPermSize，运行时常量池溢出
6. 直接内存：库函数堆外内存，也会导致OOO

## 垃圾回收

### 年轻代
* Eden, From, To
* 新建对象放Eden，放不下触发Minor GC，整理Eden, 存活对象放入From
* 如果From放不下，则继续整理From，存活对象放入To，然后交换From与To
* 如果To放不下，则放入年老代

### 年老代
* 年老代放不下对象，则触发Full GC
* 对象体积大于-XX:PretenureSizeThreshold，直接放入年老代
* 对象年龄大于XX:MaxTenuringThreshold，晋升到年老代
* Survivor相同年龄的对象超过空间的一半，则把年龄大于等于的对象晋升到年老代

### 持久代
* 大小-XX:MaxPermSize

### 分代收集算法
年代代用复制算法，年老代用标记-清除算法

### 复制算法
年轻代按照8:1:1分成3块，整理Eden区的时候，把存活对象复制到Survivor区，然后清除Eden区。

### 根搜索算法
GC root对象作为起点，搜索所有走过代路径（引用链），不可达的对象可以被回收。
GC root: Java栈的变量，本地方法栈的变量，方法区的静态变量，方法区的常量



