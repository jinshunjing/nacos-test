
# Dubbo

https://blog.csdn.net/liyue1090041509/article/details/79972508

## 源码分析
- 线程池模型

## Dubbo角色
- provider：服务提供方
- consumer：服务消费方
- registry：注册中心
- moniotr：监控中心
- container: 服务运行容器

## 协议
- dubbo: 单一长连接，NIO异步通信
- rmi: 阻塞式短连接

## 负载均衡机制
- random: 随机选择
- round robin: 轮询
- constant hash: 一致性Hash策略
- least active: 最不活跃

## 集群容错方案
- failover: 失败自动切换
- failfast: 失败直接报错
- failback: 失败自动恢复，定时重发
- failsafe: 失败安全，忽略异常
- forking: 并行调用多个，有一个成功就行
- broadcast: 广播逐个调用，忽略每个节点的异常

- 读操作采用failover, 默认重试两次
- 写操作采用failfast, 失败就报错

## 注册中心
- Zookeepr:

## 序列化框架
- hessian:
- dubbo:

## 通信框架
- netty: 

## 配置
- service
- provider
- protocol

- reference
- consumer

- registry
- monitor
- application

- argument
- method

### 当一个接口有多个实现
- 通过group来分组，provider和consumer配置相同的group

### 版本兼容
- 通过version, 多个不同的版本可以注册到注册中心

### 调用方式
- 默认同步
- 支出异步调用，Reference(async = true)

### 分布式事务
- 不支持

### 服务降级
- 含义：告诉consumer，调用服务时要做哪些动作，不操作provider
- 设置consumer的mock参数
- 向注册中心写入：override://0.0.0.0/com.foo.BarService?category=configurators&dynamic=false&application=consumer_app&mock=force:return+null
- force:return+null 消费方调用服务时直接返回null
- fail:return+null 消费方在调用失败后再返回null

### 服务暴露的过程
- ServiceConfig#export
- 本地暴露 exportLocal
- 暴露为远程服务 registryProtocol
- Invoker代理





