
# Kafka
## 如何实现exactly once? 主键幂等性


## 工作原理
* Producer发送消息到broker，Consumer从broker拉取消息
* Producer按照规则把消息发送给某个partition
* Consumer按照策略从某个或者多个partition拉取消息
* 消息管理： topic/partition，consumer group。一个partition只能被一个consumer消费

## Kafka集群
* broker有一个leader (controller), 多个follower
* broker一起取Zookeeper上注册一个临时节点，只有一个会成功，成为Leader。Leader宕机，这个临时节点会消失，其余broker再发起注册
* 每个partition也有一个leader，多个replica（备份）

## partition与broker
* 每个副本，分区0随机分配一个broker，后续分区顺序分配broker
* partition的数量大于broker的数量，可以提高吞吐率；partition的replica尽量分散到不同的broker，高可用


## partition与consumer
* partition数量大于consumer数量，每个consumer会处理多个partition
* partition数量小于consumer数量，每个consumer最多只处理一个partition,并且会有空闲consumer
* 一个partition只能被一个consumer处理
* 一个consumer可以处理多个partition

## 持久化日志
* 日志目录：如果通过log.dirs配置了多个日志目录，新的分区目录会写到分区目录数量最少的那个日志目录下，分区目录格式为{topic-分区ID}
* 日志文件：topic/partition/segment ({偏移量}.log, .index, .timeindex，文件大小为1G)
* 利用磁盘的顺序读写性能

## 消息传输一致性语义
* 最多一次
* 最少一次
* 恰好一次
* producer delivery mode 不维护offset，自增id
* consumer delivery guarantee commit 之后offset+1

## ACK机制
* 0 只要partition leader接收了，producer就返回成功
* 1 producer需要等待partition leader把消息写入
* -1 producer等待partition leader以及所有replica的ack

## Consumer rebalance
* broker的增加或者删除
* consumer的增加或者删除


