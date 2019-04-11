# Java线程
- Thread
- Runnable/Callable
- ThreadPoolExecutor
- ForkJoin
- synchronized
- Lock/Condition
- AQS

## Thread
### 状态
|状态|说明|
|------|------|
|NEW||
|RUNNABLE|已经start|
|BLOCKED|等待monitor|
|WAITING| 无期限等待|
|TIMED_WAITING|有期限等待|
|TERMINATED|被中断|

### 静态方法
- currentThread()
- holdsLock(Object)
- sleep(long)
- yield()

### 方法
- getId(), getName(), getPriority(), isDaemon()
- getState(), isAlive(), isInterrupted()
- getThreadGroup()
- getUncaughtExceptionHandler()
- start(), run()
- interrupt()
- join(long)


## Synchronized
- 通过monitorenter/monitorexit指令实现
- 存放在对象头的Mark Word
- 有4种锁：无锁，偏向锁，轻量级锁，重量级锁

## sun.misc.Unsafe
- 内存操作：allocateMemory/reallocateMemory/freeMemory

- 字段在主存的偏移：staticFieldOffset/objectFieldOffset
- 存取字段的值：getInt/putInt
- 相等才替换：compareAndSwapXX 

- park: 阻塞当前线程
- unpark: 唤醒某个线程

- monitorEnter 锁住对象
- monitorExit 解锁对象

## LockSupport
- park, unpark

### Lock
- 替换synchronized

### ReentantLock
- 可重入锁

### Condition
- 替换Object monitor，wait/notify
- 关联一个Lock
- 维护一个等待队列，通常是一个双向链表，存储的是线程对象
- 主要功能是阻塞当前线程与唤醒线程
- 阻塞当前线程：线程加入等待队列，然后执行Unsafe.park
- 唤醒某个线程：线程移出等待队列，然后执行Unsafe.unpark

### CyclicBarrier
- 使用ReentrantLock
- 在barrier上等待的线程数目达到设定的数值则执行某个操作，然后唤醒所有的线程
- 适用于map reduce的应用场景

## 异步

### Runnable
- void run()

### Callable<V>
- V call() throws Exception

### Future<V>
- 存储异步执行的返回值

### Executor
- 执行Runnable任务

### ExecutorService
- submit任务
- invoke任务
- shutdown

### ThreadPoolExecutor
构造函数的参数
|参数|说明|
|------|------|
|corePoolSize|核心线程数，一直存活|
|maxPoolSize|最大线程数，任务队列满了之后，还能创建新的线程；线程数达到最大值，则采取拒绝策略|
|blockingQueueCapacity|任务队列容量，当核心线程数达到最大值，任务先放入队列|
|rejectedExecutionHandler|任务拒绝处理器|
|keepAliveTime|线程空闲时间，线程空闲超时，退出线程池|
|allowCoreThreadTimeout|是否运行核心线程退出|

拒绝策略
|策略|说明|
|------|------|
|AbortPolicy|丢弃策略，抛出异常|
|CallerRunsPolicy|调用者运行策略|
|DiscardPolicy|忽略|
|DiscardOldestPolicy|移除最先进入队列的任务|

Executors
- newSingleThreadExecutor(1, 1, LinkedBlockingQueue)
- newFixedThreadPool(n, n, LinkedBlockingQueue)
- newCacheThreadPool(0, max, SynchronousQueue)

### ForkJoinTask<V>
- 类似与线程的实体对象
- 实现Future接口
- RecursiveAction没有返回值，RecursiveTask有返回值
- CountedCompleter
- fork - 异步计算
- join - 等待计算结果
- compute - 递归计算

### ForkJoinPool
- 执行ForkJoinTask的线程池

## AQS

