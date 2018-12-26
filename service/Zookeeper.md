
## Zookeeper集群
* 一个leader,多个follower
* server通过host而非ip， host与ip的绑定关系在/etc/hosts，特别注意本机的ip需要写出0.0.0.0，否则监听不了
* 在dataDir里添加myid文件，从1开始
* 按顺序启动，通过zkServer.sh status来查看状态

## 角色
* leader
* follower 可读可写
* observer 只读，写转发给leader
* client

## server工作状态
* LOOKING 不知道谁是leader
* LEADING 本机是leader
* FOLLOWING 本机是follower

## session
* 属性：sessionID, timeOut, tickTime, isClosing
* 状态：connecting, connected, close
* 管理：server根据expirationTime的分桶策略，client需要定期ping server来延期。client发送心跳检查，server激活session，计算新的expirationTime，并迁移session
* 清理：发起session关闭请求，收集需要清理的临时节点，删除临时节点，移除session
* 重连：connection_loss, 重连之前的server；session_moved, 重连到另一台server; session_expired，session已经被清理

## watch
* getData(), exists(), getChildren() 可以设置watch
* implements Watcher, 重载process(WatchedEvent)
* 一次性触发
* client串行执行，保证了顺序
* 轻量，只通知event，但不提供详情
* 先注册到server，response返回之后再注册到client的WatchManager
* WatchedEvent触发之后，server发送消息给client，client从WatchManager中找出所有注册的Watcher，然后串行处理

## zxid
* 64位，高32位是epoch，低32位自增counter
* epoch代表leader

## 原子广播 （ZAB协议）
* 在Paxos算法上做了改进
* leader计算zxid并发送proposal
* leader激活：该阶段，leader建立并准备发送提案
* 消息激活：该阶段，leader协调消息传输
* 第一阶段：leader按照相同的顺序propose事务给所有的follower, follower顺序ack；当leader接收到超过法定数量到ack,进入第二阶段
* 第二阶段：leader顺序commit事务给follower，follower接收到之后即可以发送给client

## 选主流程 （basic paxos）
* 推荐(myid, zxid)给所有的server
* 收集所有server的投票(myid, zxid)，并推荐最大的zxid
* 重复整个过程直到超过法定人数的server同意

## 选主流程 （fast paxos）
* 推荐自己为leader
* 其他server更新epoch和zxid，并ack接受

## 同步流程
* follower发送zxid给leader
* leader确定同步点
* 完成同步后，通知follower已经成为uptodate状态
* follower可以接收client请求

## znode类型
* persistent
* persistent_sequential
* ephemeral
* ephemeral_sequential


## 应用场景
### 配置管理
* 配置更新了通知所有节点

### 集群管理
* 选出master/leader

### 分布式锁
* 创建临时节点

### 队列管理

