
## 握手阶段
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: x3JJHMbDL1EzLkh9GBhXDw==
Sec-WebSocket-Protocol: chat, superchat
Sec-WebSocket-Version: 13

HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: HSmrc0sMlYUkAGmm5OPpG2HaGWk=
Sec-WebSocket-Protocol: chat

Sec-WebSocket-Key 拼接 258EAFA5-E914-47DA-95CA-C5AB0DC85B11，然后SHA-1，最后Base64


## 数据通信
* 数据帧（data frame）
* 控制帧（control frame）: 关闭帧(close frame)， ping/pong帧
