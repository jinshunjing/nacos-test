# Dubbo服务导出过程

## 基本流程
Dubbo服务导出过程始于Spring容器发布刷新事件，Dubbo在接收到事件后，会立即执行服务导出逻辑。整个逻辑大致可分为三个部分：
- 第一部分是前置工作，主要用于检查参数，组装URL。
- 第二部分是导出服务，包含导出服务到本地 (JVM)，和导出服务到远程两个过程。
- 第三部分是向注册中心注册服务，用于服务发现。

## 详细流程

### 入口
- 服务导出的入口方法：[dubbo-config-spring] ServiceBean#onApplicationEvent
- 可以延时导出，配置项 service.delay

### 配置检查
在导出服务之前，Dubbo需要检查用户的配置是否合理，或者为用户补充缺省配置。
- 源码：[dubbo-config-api] ServiceConfig#checkAndUpdateSubConfigs

### 多协议多注册中心
Dubbo允许我们使用不同的协议导出服务，也允许我们向多个注册中心注册服务。遍历ProtocolConfig集合导出每个服务。并在导出服务的过程中，将服务注册到注册中心。
- 多协议配置项 dubbo.protocol
- 多注册中心配置项 dubbo.registry
- 源码：[dubbo-config-api] ServiceConfig#doExportUrls

### URL组装
配置检查完成后，接下来需要根据这些配置组装URL。
- 首先是将一些信息，比如版本、时间戳、方法名以及各种配置对象的字段信息放入到map中，map 中的内容将作为URL的查询字符串。
- 构建好map后，紧接着是获取上下文路径、主机名以及端口号等信息。
- 最后将map和主机名等数据传给 URL 构造方法创建 URL 对象。
- 源码：[dubbo-config-api] ServiceConfig#doExportUrlsFor1Protocol

### 创建Invoker
Invoker 是由 ProxyFactory 创建而来，Dubbo 默认的 ProxyFactory 实现类是 JavassistProxyFactory。
- 源码：[dubbo-rpc-api] JavassistProxyFactory#getInvoker
- JavassistProxyFactory 创建了一个继承自 AbstractProxyInvoker 类的匿名对象，并覆写了抽象方法 doInvoke。覆写后的 doInvoke 逻辑比较简单，仅是将调用请求转发给了 Wrapper 类的 invokeMethod 方法。
- Wrapper 用于“包裹”目标类，Wrapper 是一个抽象类，仅可通过 getWrapper(Class) 方法创建子类。在创建 Wrapper 子类的过程中，子类代码生成逻辑会对 getWrapper 方法传入的 Class 对象进行解析，拿到诸如类方法，类成员变量等信息。以及生成 invokeMethod 方法代码和其他一些方法代码。代码生成完毕后，通过 Javassist 生成 Class 对象，最后再通过反射创建 Wrapper 实例。
- AbstractProxyInvoker 包含一个 Proxy 实例，代理了具体的服务类。
- Proxy 用于代理目标类，Proxy 是一个抽象类，仅可以通过 getProxy(ClassLoader, Class[]) 方法创建子类。可以通过 newInstance(InvocationHandler) 来创建代理实例。
- 服务类 --> Proxy --> Wrapper --> Invoker 

### 导出到本地JVM 
- 源码：[dubbo-rpc-injvm] InjvmProtocol#export
- 首先根据 URL 协议头决定是否导出服务。若需导出，则创建一个新的 URL 并将协议头、主机名以及端口设置成新的值。
- 然后创建 Invoker，并调用 InjvmProtocol 的 export 方法导出服务。
- InjvmProtocol 的 export 方法仅创建了一个 InjvmExporter，无其他逻辑。

### 导出到远程
- 源码：[dubbo-registry-api] RegistryProtocol#export
- 调用 doLocalExport 导出服务，调用 Protocol 的 export 方法
- 向注册中心注册服务
- 向注册中心进行订阅 override 数据
- 创建并返回 DestroyableExporter

### dubbo协议导出服务
默认使用dubbo协议导出服务。
- 源码：[dubbo-rpc-dubbo] DubboProtocol#export
- 源码：[dubbo-rpc-dubbo] DubboProtocol#openServer
- 源码：[dubbo-rpc-dubbo] DubboProtocol#createServer
- createServer 包含三个核心的逻辑。第一是检测是否存在 server 参数所代表的 Transporter 拓展，不存在则抛出异常。第二是创建服务器实例。第三是检测是否支持 client 参数所表示的 Transporter 拓展，不存在也是抛出异常。

### 启动服务器
默认使用Netty实现服务器。
- 源码：[dubbo-remoting-api] HeaderExchanger#bind
- getTransporter() 方法获取的 Transporter 是在运行时动态创建的，类名为 TransporterAdaptive，也就是自适应拓展类。TransporterAdaptive 会在运行时根据传入的 URL 参数决定加载什么类型的 Transporter，默认为 NettyTransporter。
- 源码：[dubbo-remoting-netty] NettyTransporter#bind
- 源码：[dubbo-remoting-netty] NettyServer#doOpen

### 注册到注册中心
注册中心默认为Zookeeper，客户端默认使用Curator。
- 源码：[dubbo-registry-zookeeper] ZookeeperRegistryFactory#createRegistry
- 源码：[dubbo-remoting-zookeeper] CuratorZookeeperTransporter#createZookeeperClient








