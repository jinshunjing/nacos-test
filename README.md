# Nacos

## 问题
1. Nacos config 2.1.0.RELEASE不能读取配置？必须2.1.1.RELEASE。可能是之前用了2.1.1，不能回到2.1.0?

## 配置管理
1. 下载Nacos wget https://github.com/alibaba/nacos/releases/download/1.1.4/nacos-server-1.1.4.tar.gz
2. 启动Nacos sh startup.sh -m standalone
3. 登陆Nacos http://localhost:8848/nacos/ nacos:nacos
4. 添加命名空间
5. 准备bootstrap.properties
6. 添加配置
7. 启动 java -jar config/target/config-1.0.0.jar --spring.profiles.active=local
8. 运行 curl http://localhost:8666/config/name

### 配置更新的原理
https://www.jianshu.com/p/acb9b1093a54
1. 客户段30s timeout 的HTTP请求
2. 服务端放入线程池，30s后执行
3. 配置更新之后，服务端通知listener，并立即返回更新后的配置给客户端


## 服务注册和发现
1. 启动 my-provider
2. 启动 my-consumer
3. 运行 curl http://127.0.0.1:8663/consume/name
