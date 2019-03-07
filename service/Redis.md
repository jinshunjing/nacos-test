
## 问题
* Redis常用的数据结构

## 为什么这么快
+ 内存操作
+ 单线程
+ 非阻塞IO：epoll

## 发布订阅
- 角色：Channel，Publisher，Subscriber

命令：
+ PUBLISH
+ SUBSCRIBE
+ UNSUBSCRIBE
+ PSUBSCRIBE
+ UNPSUBSCRIBE

### 订阅Channel
+ redisServer.pubsub_channels 字典
+ 把client添加到channel，一个channel对应多个client
+ 把channel添加到client

### 订阅模式
+ redisServer.pubsub_patterns 链表
+ 把{client,pattern}添加到链表里

### 发布消息
+ 在字典里找出channel，遍历上面的client列表，发送信息给client
+ 遍历模式列表，如果模式能够匹配channel，发送信息给client


## 数据结构
- string：int, embstr, raw,
- list：双向链表, ziplist, linked list
- hash: 映射表。数据较少时通过ziplist实现，数据较多时通过dict实现
- set: 集合，数据少时intset, 通过hash实现
- zset: 有序集合，ziplist, skiplist

### Sorted Set
- 数据较少时，通过ziplist实现。压缩链表，元素压缩编码
- 数据较多时，通过zset实现，包含一个dict和一个skiplist。dict用来查询数据到score的映射关系，skiplist用来根据score查询数据（支持范围查询）。
- dict: 字典，链表解决碰撞
- skiplist: 
> 为什么不用平衡树而用skiplist? 内存占用，范围操作，实现简单。

## 主从复制

## Sentinel

## 集群
gossip协议
cluster,数据分区，每个node管理一定数量的桶
每个node采用master-slave








