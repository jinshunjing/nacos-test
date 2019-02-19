
## 问题
* Redis常用的数据结构


## 为什么这么快
+ 内存操作
+ 单线程
+ 非阻塞IO：epoll

## 连接


## 发布订阅
+ Channel
+ Publisher
+ Subscriber

+ PUBLISH
+ SUBSCRIBE
+ UNSUBSCRIBE
+ PSUBSCRIBE
+ UNPSUBSCRIBE

### 订阅
+ redisServer.pubsub_channels 字典
+ 把client添加到channel，一个channel对应多个client
+ 把channel添加到client

### 订阅模式
+ redisServer.pubsub_patterns 链表
+ 把{client,pattern}添加到链表里

### 发布
+ 在字典里找出channel，遍历上面的client列表，发送信息给client
+ 遍历模式列表，如果模式能够匹配channel，发送信息给client


## 备份

### RDB
+ FLUSHALL和SHUTDOWN会触发RDB快照
+ 在指定的时间间隔内执行指定次数的写操作，会触发RDB快照
+ fork出一个子进程来生成RDB快照，所以不会阻塞客户端请求

### AOF
+ 每秒写一次日志
+ 当aof文件太大时，触发AOF重写，不读原来的文件，而是读内存数据

## CentOS安装Redis
下载安装包然后解压
`wget http://download.redis.io/releases/redis-3.2.12.tar.gz`
`tar -zxvf redis-3.2.12.tar.gz`

进入解压目录然后编译安装
`cd redis-3.2.12`
`make`
`make install`

修改配置文件redis.conf
允许远程访问
`#bind 127.0.0.1`

以后台方式启动：
`daemonize yes`

设置登录密码：
`requirepass Password`

设置日志文件：
`logfile "/var/log/redis.log"`

启动
`redis-server /opt/redis-3.2.12/redis.conf`

连接
`redis-cli -h 127.0.0.1 -a Password`



