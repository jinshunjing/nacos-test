

https://www.cnblogs.com/lighten/

## 架构
* Channel, ChannelHandler, ChannelHandlerContext, ChannelPipeline
* ChannelFuture, ChannelFutureListener
* EventLoopGroup, EventLoop
* ByteBuf
* ByteToMessageDecoder, MessageToByteEncoder

### 高性能
- 串行无锁化
- volatile，CAS的大量使用
- TCP参数：SO_RCVBUF, SO_SNDBUF，批量发送

### Reactor线程模型


### 粘包的策略
- 定长: FixedLengthFrameDecoder
- 分隔符: LineBasedFrameDecoder, DelimiterBasedFrameDecoder
- 消息头和消息体：消息头保护消息的总长度 LengthFieldBasedFrameDecoder

### 序列化


### 零拷贝
- 堆外内存/直接内存

### 空轮询
- CPU 100%
- 统计空select的次数，在某个周期内连续发生N次，则重建selector，注册原理的SocketChannel

### 流量整形
- GlobalTrafficShapingHandler
- 令牌桶



