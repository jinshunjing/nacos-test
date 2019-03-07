

## Java内存溢出
- 堆空间OOM，持有大量的集合类，软引用，弱引用
- 方法区OOM
- 线程数量太多，不能创建新线程
- 栈空间溢出

## JVM性能调优
- 主要是看内存的使用情况，以及GC的次数
- 修改JVM参数
- 借助jmap, jstat

## 事务的隔离级别
- 解决读数据不一致的问题：脏读，不可重复读，幻读
- 隔离级别读未提交，存在脏读，不可重复读，幻读
- 隔离级别读已提交，解决了脏读，但是存在不可重复读，幻读
- 隔离级别可重复读（多版本并发控制），解决了不可重复读，但是存在幻读
- 隔离级别串行化，解决了幻读

## 接口的幂等性
- 唯一索引
- 去重表
- 版本控制
- 状态机幂等
- Redis token机制
- source + seq

## 分布式事务的解决方案
- 本地消息表，消息队列
- 两阶段提交


## Redis单实例锁
- SET key txid NX PX 5000 (不存在才创建，并且设置过期时间)
- Lua脚本：if redis.call("get",KEYS[1]) == ARGV[1] then return redis.call("del",KEYS[1]) else return 0 end

## Redis分布式锁
- Redisson框架
- Redlock算法
- 轮流向N个节点请求锁，请求的超时时间设置成很短，这样不会因为某个节点宕机影响整个过程
- 大多数节点锁请求成功，并且总耗时不超过锁释放时间，则锁获取成功
- 如果锁获取失败，则向N个节点释放锁

## CyclicBarrier vs CountDownLatch
- CountDownLatch强调一个线程需要等待N个其他线程。不可重用。
- CyclicBarrier强调多个线程相互等待。可以重用。

## BIO,NIO,AIO
- IO包括两个阶段：等待IO事件就绪，和数据拷贝，也就是把数据从内核的缓存区拷贝到用户空间。
- BIO：阻塞的等待IO事件就绪，并且等待操作系统把数据拷贝到用户空间之后才返回
- NIO（同步非阻塞）：一般采用IO多路复用，非阻塞的等待IO事件就绪，但是数据拷贝的过程还是阻塞（同步?）的
- AIO（异步非阻塞）：非阻塞的等待IO事件就绪，并且等待操作系统把数据拷贝到用户空间之后，再通知用户进程去处理
- 注意1：同步与阻塞的含义不同，表述要准确
- 注意2: 数据拷贝的过程用说清楚是把数据从内核空间拷贝到用户空间
- 注意3: NIO要点出IO多路复用

## Command设计模式
- 命令模式的角色包括：调用者Invoker，接受者Receiver，和命令Command
- Command隔离了调用者和接受者
- 调用者调用(call)Commnd，然后由Command去执行(execute)接受者，最后由接受者完成业务逻辑(action)
- Command还可以提供undo，redo的功能：用一个辅助类（CommandManager）维护undo/redo command栈。初始化的时候undo command入栈，清空redo command栈。undo的时候，undo command出栈，放入redo command栈。redo的时候，redo command出栈，放入undo command栈。

## 一致性Hash算法
- 计算节点的Hash值，放入2^32的圆上
- 为了均匀，可以加入虚拟节点，即一个物理节点对应多个虚拟节点
- 数据计算Hash值，然后放入顺时针的第一个节点
- 添加/删除节点只影响它逆时钟方向的数据
- 注意1：Hash值的范围是0到2^32-1，即int
- 注意2: 数据沿着顺时针方向放入节点
- 注意3: 使用虚拟节点的技巧

## Kafka消息送达的确认
- 消息发送分异步和同步两种方式
- 异步时，现在客户端缓存然后批量发送
- 通过配置acks来确定消息送达
- acks=0不确认
- acks=1表示leader接收成功就确认
- acks=all表示要求所有的副本都接收成功才确认

## ZK的watch机制

## 缓存击穿
- 多线程同时缓存不命中，要去查询数据库
- 方案一：后台Job刷新缓存，避免缓存击穿问题，比如行情数据
- 方案二：查询数据库加锁，ReentrantLock, 分布式锁（Redis/Zookeeper）

## ReentrantLock和Condition
- 替代synchronized和wait/notify
- ReentrantLock#lock, #unlock
- Condition#await, signal

## Map Reduce
- Java Map Reduce
- Hadoop Map Reduce

## 服务雪崩
- 雪崩效应：服务的级联崩溃，比如服务提供者被拖死，导致服务调用者阻塞，导致更多的服务崩溃

- 超时策略: 超时时间短
- 熔断机制：使用断路器 Hystrix

- Hystrix 线程隔离： Tomcat线程池耗尽，
- Hystrix 熔断机制

- 资源隔离模式

- 服务隔离
- 服务熔断
- 服务降级

## 高可用
- 冗余
- 自动故障转移

## 高并发
- 水平扩展

## 分库分表
- Hash一致性
- 路由策略，路由规则
- 按照时间切片
- 按照Hash切片

## Java动态代理
- 定义接口，创建真实对象实例
- 创建代理实例：Proxy#newProxyInstance(ClassLoader, 接口，InvocationHandler)
- 代理方法的调用：InvocationHandler#invoke(代理对象实例，Method, 参数列表)
- InvocationHandler持有真实对象









