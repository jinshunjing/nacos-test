# MySQL

## 问题
* 手写SQL？

## 事务
事务的ACID属性
- A原子性：全部执行或者全部不执行
- C一致性：开始和完成时，数据保持一致性
- I隔离性：独立执行
- D持久性：数据的修改是永久性的

并发事务处理的问题
- 脏读：一个事务查询了另一个事务未提交的数据更新
- 不可重复读：一个事务重新查询，发现了另一个事务更新的数据
- 幻读：一个事务重新查询，发现了另一个事务插入的数据
- 更新丢失：一个事务覆盖了另一个事务的数据更新

事务隔离级别（读数据一致性）：
- 读未提交 read uncommited：脏读，幻读，不可重复读
- 读已提交 read commited：幻读，不可重复读
- 可重复读 repeatable read：幻读
- 可串行化 serializable

## InnoDB锁机制
### 乐观锁
读取数据的时候不加锁，更新数据的时候会判断数据是否被修改。一般通过版本号或CAS实现

### 悲观锁
读取数据的时候会加锁。表锁，行锁，共享锁，排他锁，都是悲观锁

### 表锁
- 意向共享锁：事务给一个数据行加共享锁之前必须先取得该表的意向共享锁
- 意向排他锁：事务给一个数据行加排他锁之前必须先取得该表的意向排他锁

### 行锁
* 共享锁：允许事务去读一行，阻止其他事务获取该数据集的排他锁
* 排他锁：允许事务更新数据，阻止其他事务获取该数据集的共享读锁与排他写锁

行锁通过给索引项加锁实现，而不是给记录加锁。只有通过索引检索才使用行锁，否则使用表锁。

INSERT,UPDATE,DELETE语句自动加排他锁。

SELECT语句需要手动加锁：
- 共享锁：SELECT ... LOCK IN SHARE MODE
- 排他锁：SELECT ... FOR UPDATE

### 间隙锁
* 使用范围条件检索，不存在的记录也会被加锁
* 使用相等条件检索也会给不存在的记录加锁
* 能够将右边的记录加锁：SELECT MAX(...) ... FOR UPDATE

### 特殊的锁
INSERT INTO ... SELECT, CREATE TABLE ... SELECT 语句会给源表加锁


## MySQL事务隔离级别的实现
### 可重复读
MVCC多版本并发控制：记录增加两个隐藏列，创建版本号，删除版本号。更新的时候删除旧记录，创建新记录。查询的时候需满足：
* 创建版本号小于等于事务版本号
* 删除版本号大于事务版本号

### 幻读
读的时候加共享锁或者排他锁

# MyBatis

### 技巧
* "#{}" 防止SQL注入
* 批量插入：insert ... values (...), (...), ...
* 插入或者更新：insert ... on duplicate key update ...
* 插入的自增主键： select last_insert_id();
* 批量更新：update ... set c1 = case c2 when a1 then b1 when a2 then b2 end ...


# Spring

## 事务的传播行为
* PROPAGATION_REQUIRED: 默认值。如果没有则新建事务，如果有则加入当前事务
* PROPAGATION_REQUIRES_NEW: 如果没有则新建事务，如果有则挂起当前事务
* PROPAGATION_NESTED: 如果没有则新建事务，如果有则新建当前事务的子事务
* PROPAGATION_SUPPORTS: 如果没有则非事务，如果有则加入当前事务
* PROPAGATION_NOT_SUPPORTED: 如果没有则非事务，如果有则挂起当前事务
* PROPAGATION_MANDATORY: 如果没有则抛出异常，如果有则加入当前事务
* PROPAGATION_NEVER: 如果没有则非事务，如果有则抛出异常

## @Transactional
* 只能作用于public方法

# MongoDB

## BSON: Binary JSON

### _id
"_id" : ObjectId("5ba1e274d276de40947b06b6")
时间戳：5ba1e274
机器标识码：d276de
进程ID：4094
随机数：7b06b6

### Double, NumberInt, NumberLong
默认是Double类型