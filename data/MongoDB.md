
# MongoDB

## 使用
* 默认的数据库路径 -dbpath=/data/db


## 设计原则
* 需要单独访问的对象不要内嵌
* 需要经常更新的字段不要冗余
* 一对很少：直接内嵌
* 一对许多：比如一个block包含多个tx，在block加入tx主键的数组，也可以加入一些tx的冗余字段
* 一对非常多：比如一个公司有非常多的员工，在员工加入公司的主键，也可以加入一些公司的冗余字段
* 多对多：使用双向关联，加入主键的数组




## BSON: Binary JSON

### _id
"_id" : ObjectId("5ba1e274d276de40947b06b6")
时间戳：5ba1e274
机器标识码：d276de
进程ID：4094
随机数：7b06b6

### Double, NumberInt, NumberLong
默认是Double类型
