# Netty多线程

### 异步
- Future: 重写JDK Future，#get
- Promise: 主线程可写，#addListener

- AbstractFuture
- DefaultPromise
- DefaultChannelPromise


### 接口
- EventExecutorGroup：通过next管理多个EventExecutor；通过submit提交一个任务；通过schedule调度一个任务
- EventExecutor：通过inEventLoop检查当前线程是否在EventLoop中
- OrderedEventExecutor：顺序处理任务

- EventLoopGroup：通过next管理多个EventLoop；通过register注册一个Channel
- EventLoop：事件循环

### 实现类
- AbstractEventExecutor：safeExecute执行任务
- AbstractScheduledEventExecutor：维护一个优先队列
- SingleThreadEventExecutor：runAllTasks运行任务
- SingleThreadEventLoop： 
- NioEventLoop：Selector, run, 

### EventLoop 线程功能
- 串行化，在同一个线程里处理，减少多线程竞争锁
- 每一批次处理，可以设置I/O事件和任务处理的比率
- 每一批次处理，如果数据量太大，可以新建任务推迟到下一批次
- 每一批次处理，如果输出缓冲区已经写满，可以设置I/O写事件推迟到下一批次
- 处理I/O事件，如果一次没有处理完，可以设置OPS标记位继续处理
- 处理定时任务
- 处理任务。为了高性能，每次可能处理不了所有的数据，此时可以创建任务在下一次继续处理


