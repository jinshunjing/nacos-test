

# 高并发I/O

### I/O的两个阶段
* 等待数据就绪
* 将数据从内核缓冲区拷贝到用户缓存区

## Kernal层I/O五种模型
* 阻塞式I/O
* 非阻塞式I/O：一次检查一个文件描述符的就绪状态
* I/O多路复用：一次检查多个文件描述符的就绪状态：select, poll, epoll
* 信号驱动I/O
* 异步I/O

### 多线程并发模型
一个主线程，多个工作线程，通常使用线程池

### I/O多路复用模型select/poll/epoll
* select：检查的文件描述符作为参数传入，数量限制在1024
* poll：去除了数量限制，但是依然要从用户态拷贝到内核态，并且要遍历所有传入的文件描述符
* epoll：利用mmap技术避免了拷贝和遍历操作。通过epoll_ctl注册，通过epoll_wait得到就绪通知

### I/O多路复用模型epoll
把select/poll调用分成3个部分：
* epoll_create: 建立一个epoll对象
* epoll_ctl: 向epoll对象添加socket链接。文件描述符只拷贝一次。
* epoll_wait: 收集发生事件的链接。为每个文件描述符指定一个回调函数，放入就绪表。

## 网络I/O三种并发模型
* BIO
* NIO: Reactor pattern 反应堆模式
* AIO: Proactor pattern

### Reactor Pattern
* Reactor: 
* Event Demultiplexer: 事件多路复用/事件分离器/事件多路分配器
* Acceptor: 也作Selector, 管理网络连接
* Handle: 
* Dispatcher: 管理Handler的注册，从Acceptor获取事件，然后分发给Handler
* Event Handler: 请求的处理
* 事件多路分离器Reactor等待就绪事件
* 事件多路分离器使用Event Loop等待IO就绪事件到来，然后执行回调函数进行IO

### Proactor Pattern
* Initiator: 调用Asynchronous Operation Processor
* Asynchronous Operation Processor: 调用Asynchronous Operation发起异步IO，并获取IO完成通知
* Asynchronous Operation: 异步操作
* Completion Event Queue: 完成事件队列
* Proactor: 
* Asynchronous Event Demultiplexer: 异步事件多路复用
* Completion Dispatcher: 分发IO完成通知给对应的Completion Handler
* Completion Handler: 回调
* 事件多路分离器Proactor等待事件完成
* 直接返回，由内核处理IO，事件多路分离器等待IO事件完成之后再执行回调函数



