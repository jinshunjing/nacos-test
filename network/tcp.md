# Linux

## netstat

### Server Socket
netstat -ltnp

### Socket
netstat -tnp


### TCP状态
状态 | 说明
:------ | :------
CLOSED | 关闭状态
LISTEN | 服务端监听状态
SYN_SENT | 客户端已发送SYN
SYN_RECV | 服务端已接收SYN
ESTABLISHED | 正常数据传输状态
FIN_WAIT_1 | 客户端已发送FIN，等待服务端确认
CLOSE_WAIT | 服务端已接收FIN并且发送ACK，等待直接关闭连接
FIN_WAIT_2 | 客户端已接收ACK，等待服务端发送FIN
LAST_ACK | 服务端已发送FIN，等待客户端确认
TIME_WAIT | 客户端已接收FIN，等待2MLS后关闭连接
CLOSING | 两边同时发送FIN

### 三次握手
* 第一次握手：客户端发送连接请求报文（SYN=1，seq=x），进入状态SYN_SENT
* 第二次握手：服务端接收到报文，发送确认报文（SYN=1，ACK=1, ack=x+1, seq=y)，进入状态SYN_RECV
* 第三次握手：客户端接收到报文，发送确认报文（ACK=1, ack=y+1, seq=x+1），进入状态ESTABLISHED
* 服务端接收到报文，进入状态ESTABLISHED

> 为什么还需要客户端再确认一次？
为了防止失效的连接请求被服务端确认而导致的错误。
比如客户端发送了两次连接请求，后一个请求比前一个请求先到达服务端。
服务端如果也确认了后到达到连接请求，客户端可以忽略。

> 服务端容易受到SYN攻击。服务端在第二次握手之后就分配资源。


### 四次挥手
* 第一次挥手：客户端发送连接释放报文（FIN=1, seq=u），进入状态FIN_WAIT_1
* 第二次挥手：服务端接收到报文，发送确认报文(ACK=1, ack=u+1, seq=v)，进入状态CLOSE_WAIT
* 客户端接收到报文，进入状态FIN_WAIT_2
* 第三次挥手：服务端发送连接释放报文(ACK=1, FIN=1, ack=u+1, seq=w)，进入状态LAST_ACK
* 第四次挥手：客户端接收到报文，发送确认报文（ACK=1, ack=w+1），进入状态TIME_WAIT，等待2MLS后进入状态CLOSED
* 服务端接收到报文，进入状态CLOSED

> 为什么还需要客户端再等待2MLS？
保证客户端的确认报文能够到达服务端。客户端可以超时重传确认报文。
防止失效的连接请求报文出现。

> 为什么需要四次挥手？
服务端可能还需要发送数据，不能立即关闭连接。


### TCP KeepAlive
* tcpkeepalivetime: TCP连接在多少秒没有数据报文传输之后发送探测报文
* tcpkeepaliveintvl: 两次探测报文间隔多少秒
* tcpkeepaliveprobes: 探测的次数

默认KeepAlive不开启。如果长时间不传输数据报文，不知道连接是否有效。

### HTTP Keep-Alive
复用已有的TCP连接。默认开启。

