# MySQL

## 问题
* 手写SQL：差集，交集，

## SQL语句

### ON DUPLICATE KEY UPDATE ...
- 存在不插入，不存在才插入

### UNION vs UNION ALL
* UNION ALL不判重，不排序
* UNION 会判重，会排序

### GROUP BY ... HAVING ...
* HAVING 后面必须是聚合函数的返回值

### SELECT ... FROM a, b where a.id = b.id ...
* 联表查询

### SELECT ... FROM a INNER JOIN b on a.id = b.id ..
* 内连接查询

### SELECT ... FROM a LEFT OUTER JOIN b on a.id = b.id .
* 外连接查询

### LIMIT a, b
- a是偏移，从0开始
- b是记录数目

### LIMIT b
- b是记录数目

### 条件
- WHERE 
- GROUP BY
- HAVING
- ORDER BY
- LIMIT

### CASE WEHN ... THEN ... ELSE ... END


## InnoDB锁机制
### 乐观锁
读取数据的时候不加锁，更新数据的时候会判断数据是否被修改。
一般通过版本号或CAS实现。

### 悲观锁
读取数据的时候会加锁。
表锁，行锁，共享锁，排他锁，都是悲观锁

### 表锁
- 意向共享锁：事务给一个数据行加共享锁之前必须先取得该表的意向共享锁
- 意向排他锁：事务给一个数据行加排他锁之前必须先取得该表的意向排他锁

### 行锁
* 共享锁：允许事务去读一行，阻止其他事务获取该数据集的排他锁
* 排他锁：允许事务更新数据，阻止其他事务获取该数据集的共享读锁与排他写锁

> 要点：行锁通过给索引项加锁实现，而不是给记录加锁。只有通过索引检索才使用行锁，否则使用表锁。

INSERT,UPDATE,DELETE语句自动加排他锁。

SELECT语句需要手动加锁：
- 共享锁：SELECT ... LOCK IN SHARE MODE
- 排他锁：SELECT ... FOR UPDATE

### 间隙锁
* 使用范围条件检索，不存在的记录也会被加锁
* 使用相等条件检索也会给不存在的记录加锁
* 能够将右边的记录加锁：SELECT MAX(...) ... FOR UPDATE

### 特殊的锁
* 给源表加锁：INSERT INTO ... SELECT, CREATE TABLE ... SELECT

## MVCC下
- 插入：创建版本号是系统版本号，不是执行事务的ID
- 删除：删除版本号是系统版本号

### 幻读问题
- 事务2插入新记录。事务1修改该记录，然后就可以读出该记录

### Next-Key Lock
- 包含行锁和间隙锁，用于防止幻读

### read view
- 事务的第一个Select语句创建read view，对应未分配的系统事务ID
- 看不到read view创建时活跃的事务
- 看不到read view以后创建的事务

## 主从复制
- 主：binlog dump线程 - SQL更新语句记录在binlog
- 从：io线程 - 拉取master的binlog，写入自己的relay log
- 从：SQL执行线程 - 执行relay log里的语句
- 基于SQL语句的复制：binlog小，有些语句无法被复制
- 基于行的复制：可靠性高，任何情况都可以复制，binlog大
- 混合复制：两种方式都可以用

## delete
- 不删除，这是加个标志，等到后面的purge线程来删除
- undo log保留，因为MVCC需要用到

## update
- 修改非主键，加一个反向的undo log
- 修改主键，先删除再添加新记录

# MyBatis

### 技巧
* "#{}" 防止SQL注入
* 批量插入：insert ... values (...), (...), ...
* 插入或者更新：insert ... on duplicate key update ...
* 插入的自增主键： select last_insert_id();
* 批量更新：update ... set c1 = case c2 when a1 then b1 when a2 then b2 end ...


# Spring

## @Transactional
* 只能作用于public方法
* 动态代理，只能作用于原始对象，this.call() 无效


