
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

### sun.misc.Unsafe
* park 阻塞当前线程
* unpark 唤醒某个线程
* monitorEnter 锁住对象
* monitorExit 解锁对象
* compareAndSwapXX 相等才替换

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


