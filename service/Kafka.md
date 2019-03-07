
# Kafka

## 工作原理
- 角色：broker, producer, consumer
- Producer发送消息到broker，Consumer从broker拉取消息
- Producer按照规则把消息发送给某个partition
- Consumer按照规则从某个或者多个partition拉取消息

## 消息管理
- topic，partition，consumer group
- 一个topic可以配置多个partition
- 一个partition有多个副本，其中一个是leader，其余是follower/replica
- 一个partition可以被多个consumer group消费，但是同一个consumer group只有一个consumer能消费

### partition与broker
- 每个副本，分区0随机分配一个broker，后续分区顺序分配broker
- partition的数量大于broker的数量，可以提高吞吐率
- partition的replica尽量分散到不同的broker，高可用

### partition与consumer
- partition数量大于consumer数量，每个consumer会处理多个partition
- partition数量小于consumer数量，每个consumer最多只处理一个partition,并且会有空闲consumer
- 一个partition只能被一个consumer处理
- 一个consumer可以处理多个partition

## Zookeeper的使用
- broker, consumer
- partition与broker的配置存储在ZK
- partition与consumer的配置存储在ZK
- producer和consumer连接broker(列表), broker连接ZK

## Broker在ZK上的配置
- /brokers/ids/{0}: "host":"","port":9092，每个broker
- /brokers/topics/{topic}: "partitions":{"0":[0,1,2]}，每个topic
- /brokers/topics/{topic}/partitions/{0}/state: "leader":0, "isr":[0,1,2]，每个partition

## Consumer在ZK上的配置
- /consumers/{group}/ids/{consumer id}{进程ID}: "subscribtion":{"{topic}":{partition}}，每个consumer订阅的partition 
- /consumers/{group}/owners/{topic}/{partition}: consumer id + 进程ID，每个partition的consumer 
- /consumers/{group}/offsets/{topic}/{partition}: offset，每个partition的offset

## Spring使用Kafka
- yml里配置bootstrap-servers，连接多个broker，避免单点故障
- producer和consumer只连接broker
- broker配置ZK节点列表
- producer和consumer会记住要连接的broker，如果不是目标broker，会自动路由到目标broker
- producer先连接任意一个broker，broker查询ZK，转发到partition的leader。维护多个broker的信息
- consumer先连接任意一个broker，broker查询ZK，到partition的leader拉取消息

## producer发送消息
- 消息发送分异步和同步两种方式
- 异步时，先在客户端缓存然后批量发送
- 通过配置acks来确定消息送达
- acks=0不确认
- acks=1表示leader接收成功就确认
- acks=all表示要求所有的副本都接收成功才确认

## consumer拉取消息
- 轮询
- 可以阻塞
- fetch.min.bytes/fetch.max.bytes
- fetch.max.wait.ms

## Consumer Polling
- 通过poll()的心跳机制来维持连接
- max.poll.interval.ms: 超过该时间不调用poll，broker会认为consumer已经挂了
- max.poll.records：限制每次poll返回的消息数目

## Consumer Offset
- enable.auto.commit: 自动或者手动提交offset
- auto.commit.interval.ms
- consumer.commitSync()：手动提交offset。使用场景：需要累积到一定量的消息之后，才批处理。批处理成功之后才commit
- 手动提交可能会失败，导致重复消费消息。所以consumer要幂等

## Consumer Partition
- 手动配置partition: consumer.assign()

## Consumer rebalance
- consumer加入/退出consumer group
- 新的partition加入topic
- 新的topic可以通过regex订阅
- 动态维持consumer与partition的关联

## RocketMQ延迟消息
- 类似hash：预定义了18个level，每个level的延迟时间相同，有自己的定时器，所以可以顺序处理，避免了排序
- 用优先队列，每次取根，判断是否投递

## 持久化日志
* 日志目录：如果通过log.dirs配置了多个日志目录，新的分区目录会写到分区目录数量最少的那个日志目录下，分区目录格式为{topic-分区ID}
* 日志文件：topic/partition/segment ({偏移量}.log, .index, .timeindex，文件大小为1G)
* 利用磁盘的顺序读写性能




