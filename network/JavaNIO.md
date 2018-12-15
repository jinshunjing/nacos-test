

## 内容
* nio 1.4引入
* nio 2.0 1.7引入，主要是aio
* nio.channels 1.4引入
* nio.charset 1.4引入
* nio.file 1.7引入

## IO模型
* 阻塞IO
* 非阻塞IO
* 多路复用IO
* 信号驱动IO
* 异步IO

## ByteFactory
* newByteBuffer
* newDirectByteBuffer

### Java IO vs NIO
* 面向流，面向缓存
* 阻塞，非阻塞
* 选择器，一个线程处理多个I/O通道

### 模型
* Buffer: capacity, position, limit, flip(), rewind(), clear(), compact(), mark(), reset()
* CharSet: encode, decode
* Channel: FileChannel, DatagramChannel, SocketChannel, ServerSocketChannel, Pipe (SourceChannel, SinkChannel)
* Selector

### Selector
* Selector.open(): 创建selector
* SelectableChannel.register(...): 将channel注册到selector
* Selector.select(): 选择事件就绪的通道
* Selector.selectedKeys(): 事件就绪的通道
* Selector.wakeUp(): 唤醒selector
* Selector.close(): 关闭selector

