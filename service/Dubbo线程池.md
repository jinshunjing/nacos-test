# Dubbo线程池源码分析

## 源码
- 模块：dubbo-common
- 包：org.apache.dubbo.common.threadpool

## ThreadPool
- 定义SPI接口ThreadPool，
- 提供方法getExecutor获取线程池
- 默认采用FixedThreadPool

## FixedThreadPool
- 线程池中线程的数量固定不变；线程池满了就放队列，队列满了就拒绝；
- 线程数通过参数threds配置，默认200；
- 等待队列大小通过参数queues配置，默认0，使用SynchronousQueue；正数使用固定容量的LinkedBlockingQueue；负数使用无容量限制的LinkedBlockingQueue；
- 拒绝策略是Abort，抛出异常；

## LimitedThreadPool
- 线程池中线程的数量只能增加不能减少，但是有一个上限；先放队列，队列满了新建线程；线程池满了就拒绝；
- core线程数通过corethreads配置，默认为0；最大线程数通过threads配置，默认200；
- keepAlive无穷大，线程被创建之后就不会被回收；
- 等待队列与拒绝策略与FixedThreadPool相同；

## CachedThreadPool
- 线程池的容量无穷大，但是空闲线程会被过期回收；先放对了，队列满了新建线程；
- core线程数通过corethreads配置，默认为0；最大线程数通过threads配置，默认无穷大；
- keepAlive通过alive配置，默认1分钟；
- 等待队列与拒绝策略与FixedThreadPool相同；

## EagerThreadPool
- core线程都busy时，新建线程来处理task，而不是放入等待队列；有空闲线程时则放入等待队列，等待线程处理；
- 扩展出EagerThreadPoolExecutor和TaskQueue；
- core线程数通过corethreads配置，默认为0；最大线程数通过threads配置，默认无穷大；
- keepAlive通过alive配置，默认1分钟；
- 等待队列大小通过queues配置，默认为1；

### EagerThreadPoolExecutor
- 重载execute方法；
- 统计已提交的task数量；
- 被拒绝了，尝试直接放入等待队列；

### TaskQueue
- 重载offer方法；
- 如果有空闲线程(已提交的task数量小于线程池中线程的数量)，则把task插入队列，等待空闲线程来处理；
- 如果线程池中线程的数量小于线程池的上限，则task不插入队列，而是新建线程来处理；
- 如果线程池中线程的数量已经达到线程池的上限，则把task插入队列；
