

# 高并发I/O
### 多线程并发模型
一个主线程，多个工作线程，通常使用线程池

### I/O多路复用模型select/poll
* select：支持的文件描述符有数量限制
* poll：没有数量限制，依然要从用户态拷贝到内核态，并且要遍历所有传入的文件描述符

### I/O多路复用模型epoll
* 对文件描述符没有数量限制

把select/poll调用分成3个部分：
* epoll_create: 建立一个epoll对象
* epoll_ctl: 向epoll对象添加socket链接。文件描述符只拷贝一次。
* epoll_wait: 收集发生事件的链接。为每个文件描述符指定一个回调函数，放入就绪表。

# Java NIO
### Java IO vs NIO
* 面向流，面向缓存
* 阻塞，非阻塞
* 选择器，一个线程处理多个I/O通道

### 模型
* Channel: FileChannel, DatagramChannel, SocketChannel, ServerSocketChannel, Pipe (SourceChannel, SinkChannel)
* Buffer: capacity, position, limit, flip(), rewind(), clear(), compact(), mark(), reset()
* Selector

### Selector
* Selector.open(): 创建selector
* SelectableChannel.register(...): 将channel注册到selector
* Selector.select(): 选择事件就绪的通道
* Selector.selectedKeys(): 事件就绪的通道
* Selector.wakeUp(): 唤醒selector
* Selector.close(): 关闭selector




