
## 问题
* 线程池的实现机制？
* 线程池的拒绝策略？
* 死锁的一些场景？

## Thread
* 线程的管理？
* 线程的生命周期？
* 线程的阻塞？
* 线程的中断？

### Thread.State
* NEW
* RUNNABLE
* BLOCKED 等待monitor lock
* WAITING 无期限等待
* TIMED_WAITING
* TERMINATED
* interrupted status

### 静态方法
* currentThread()
* holdsLock(Object)
* sleep(long)
* yield()

### 方法
* getId(), getName(), getPriority(), isDaemon()
* getState(), isAlive(), isInterrupted()
* getThreadGroup()
* getUncaughtExceptionHandler()
* start(), run()
* interrupt()
* join(long) 等待线程运行结束

## 锁

### CAS Compare-And-Swap 比较交换

### 对象头 Object Header
* Mark Word
* 无锁时：hash + age + 001
* 偏向锁：线程ID + epoch + age + 101
* 轻量级锁：指向栈帧中锁记录的指针 + 00
* 重量级锁：指向互斥量的指针 + 10
* GC标记：空 + 11

* 指向类型的指针

### synchronized
* https://blog.csdn.net/tongdanping/article/details/79647337
* https://www.jianshu.com/p/d53bf830fa09
* 通过进入/退出monitor对象来实现同步：指令monitorentr/monitorexit
* 锁对象存在对象头MarkWord
* 锁的4种状态：无锁，偏向锁，轻量级锁，重量级锁
* 偏向锁：一个线程多次获得同一个锁，MarkWord记录锁偏向的线程。如果在线程1持有锁期间有线程2来请求，则升级为轻量级锁
* 轻量级锁：竞争线程不多，持有时间不长。如果线程1持有轻量级锁，线程2请求会自旋，再来一个线程3，则会升级成重量级锁，不允许线程3自旋


### sun.misc.Unsafe
* https://www.cnblogs.com/gxyandwmm/p/9418915.html
* 内存操作：allocateMemory/reallocateMemory/freeMemory

* 字段在主存的偏移：staticFieldOffset/objectFieldOffset
* 存取字段的值：getInt/putInt
* 相等才替换：compareAndSwapXX 

* park 阻塞当前线程
* unpark 唤醒某个线程

* monitorEnter 锁住对象
* monitorExit 解锁对象


### LockSupport
* park, unpark

### Lock
* 替换synchronized

### ReentantLock
* 可重入锁

### Condition
* 替换Object monitor
* 关联一个Lock
* 维护一个等待队列，通常是一个双向链表，存储的是线程对象
* 主要功能是阻塞当前线程与唤醒线程
* 阻塞当前线程：线程加入等待队列，然后执行Unsafe.park
* 唤醒某个线程：线程移出等待队列，然后执行Unsafe.unpark

### CyclicBarrier
* 使用ReentrantLock
* 在barrier上等待的线程数目达到设定的数值则执行某个操作，然后唤醒所有的线程
* 适用于map reduce的应用场景

## 异步

### Runnable
* void run()

### Callable<V>
* V call() throws Exception

### Future<V>
* 存储异步执行的返回值

### Executor
* 执行Runnable任务

### ExecutorService
* submit任务
* invoke任务
* shutdown

### ThreadPoolExecutor
* 线程池执行者

### ForkJoinTask<V>
* 类似与线程的实体对象
* 实现Future接口
* RecursiveAction没有返回值，RecursiveTask有返回值
* CountedCompleter
* fork - 异步计算
* join - 等待计算结果
* compute - 递归计算

### ForkJoinPool
* 执行ForkJoinTask的线程池


