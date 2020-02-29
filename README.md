# Nacos

## 配置管理
1. 下载Nacos wget https://github.com/alibaba/nacos/releases/download/1.1.4/nacos-server-1.1.4.tar.gz
2. 启动Nacos sh startup.sh -m standalone
3. 登陆Nacos http://localhost:8848/nacos/ nacos:nacos
4. 添加命名空间
5. 准备bootstrap.properties
6. 添加配置
7. 运行 curl http://localhost:8662/config/name

### 配置更新的原理
https://www.jianshu.com/p/acb9b1093a54
1. 客户段30s timeout 的HTTP请求
2. 服务端放入线程池，30s后执行
3. 配置更新之后，服务端通知listener，并立即返回更新后的配置给客户端

