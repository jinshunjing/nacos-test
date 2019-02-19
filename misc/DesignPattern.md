# 设计模式

## 24种设计模式
### 创建型模式
+ 抽象工厂模式abstract factory：
+ 生成器模式builder：
+ 工厂模式factory：
+ 原型模式prototype：
+ 单例模式singleton：
+ 多例模式multition:

### 结构型模式
+ 适配器模式adapter：
+ 桥接模式bridge：
+ 组合模式composite：
+ 装饰器模式decorator：
+ 外观模式facade：
+ 享元模式flyweight：
+ 代理模式proxy：

### 行为型模式
+ 责任链模式chain of responsibility：使多个对象都有机会处理请求。将对象连成一条链，并沿着这条链传递请求。
Handler#handleRequest()
可以引入一个HandlerChain类，也可以直接Handler#next()

+ 命令模式command：将请求封装成对象，使请求发送者和请求接受者之间相互隔离
Invoker#call; Receiver#action; Command#execute,Receiver

+ 备忘录模式memento：快照模式，数据备份机制。发起者Originator只关系备忘录的创建和恢复，备忘录存储在管理者Caretaker
Originator#createMemento(),#restoreMemento(Memento),state; Memento.state; Caretaker#saveMemento(Memento),#getMemento()

+ 解释器模式interpreter：定义语言的文法，并且建立解释器来解释该语言中的句子
Expression#interpret(Context)

+ 观察者模式observer：

+ 访问者模式visitor：作用于一个对象结构ObjectStruct中各个元素Element的操作accept。在不改变元素类的前提下定义元素的新操作。数据结构稳定，但是操作可以自由演化。
Visitor#visit(Element); Element#accept(Visitor); ObjectStruct#accept(Visitor),List<Element>

+ 模版方法模式template：

+ 策略模式strategy：

+ 状态模式state：当对象的状态改变时，允许其改变行为。客户端调用Context。状态对象State既要处理当前状态，也要处理如何过渡到下一个状态。
Context#request(),#getState(),#setState(State),state; State#handle()

+ 中介者模式mediator：

+ 迭代器模式iterator：

### J2EE模式
+ MVC模式：
+ 业务代表模式business delegate：
+ 组合实体模式composite entity：
+ 数据访问对象模式data access object：
+ 前端控制器模式front controller：
+ 拦截过滤器模式intercepting filter：
+ 服务定位器模式service locator:
+ 传输对象模式transfer object：



## 7大设计原则
+ 单一职责原则
+ 里氏替换原则：继承与派生的规则，尽量不要重写父类的方法
+ 依赖倒置原则：针对接口编程
+ 接口隔离原则：接口不要太臃肿
+ 迪米特法则：低耦合，高内聚
+ 开闭原则：对扩展开放，对修改关闭
+ 组合/聚合复用原则：多用组合/聚合，少用继承


## Command模式
### 定义
将请求封装成命令对象，实现请求调用者与请求接收者的解藕。
命令接口Command：execute, undo, redo
具体命令：实现命令接口
调用者Invoker：持有命令，并调用命令方法
接收者Receiver：接收命令并执行

### 适用场景
1. 系统要求请求调用者与请求接收者解藕
2. 系统要求请求排队，在不同的时间执行
3. 系统要求支持命令的Undo与Redo
4. 系统要求将命令组合，形成宏
