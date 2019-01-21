
## SSL证书

### 申请证书
* SSL Server生成私钥/公钥：server.key/server.pub
* SSL Server生成server.req/server.csr，包含域名/公钥/申请者
* CA用ca.key签名server.req，生成server.crt，
* SSL Client用ca.crt解密server.crt

> server.crt无法伪造，因为它是由ca.key生成，并且只能被ca.pub解密

### 单向认证
* SSL Server: server.key, server.crt
* SSL Client: ca.crt

### 双向认证
* SSL Server: server.key, server.crt, ca.crt
* SSL Client: client.key, client.crt, ca.crt

### 认证流程
* client发送明文随机数random_c
* server发送明文随机数random_s，加密算法
* client生成随机数pre-master，用server.pub加密后传输；并用对称密钥(random_c, random_s, pre-master)签名之前所有接受信息的hash
* server计算对称密钥(random_c, random_s, pre-master), 并验证签名
* 握手结束，使用对称密钥加密


