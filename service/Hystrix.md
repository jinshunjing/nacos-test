
# Hystrix

## 容错框架
- 线程池隔离
- 信号量隔离
- 降级策略
- 熔断机制

## 命令模式
- 把业务请求封装成命令请求


## 资源隔离模式
- 默认采用线程池隔离机制
- 信号量隔离机制

### 线程池隔离机制
- 每个command对应一个线程池
- #execute 同步调用
- #queue 异步调用

## 熔断机制
- 