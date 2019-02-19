
## 问题
### 什么是Nginx
Http服务器，反向代理，负载均衡

### Nginx工作原理
* 一个Master多个Worker
* 单线程
* 异步非阻塞事件处理机制：epoll模型

## 配置

### SSL
* ssl_certificate
* ssl_certificate_key
* ssl_ciphers

### 反向代理
* location
* proxy_pass 有URI会替换（尤其是/），没有URI原样保留
* rewrite

### upstream
* ip_hash
* weight


## CentOS 7 安装 Nginx 1.12.2
安装
`yum install nginx`

配置文件
/etc/nginx/nginx.conf
日志文件
/var/log/nginx

启动
`nginx -c /etc/nginx/nginx.conf`

检查配置文件
`nginx -t`
重新加载配置文件
`nginx -s reload`

