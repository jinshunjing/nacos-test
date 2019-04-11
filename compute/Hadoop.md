
面试题
https://www.cnblogs.com/jchubby/p/5449379.html


## Map Reduce

## Mapper

extends Mapper<LongWritable, Text, Text, IntWritable>
输入key类型，输入value类型，输出key类型，输出value类型

void map(LongWritable, Text, Context)
输入key类型，输入value类型

context.write(Text, IntWritable)
输出key类型，输出value类型


## Reducer

extends Reducer<Text, IntWritable, Text, IntWritable>
输入key类型，输入value类型，输出key类型，输出value类型

void reduce(Text, Iterable<IntWritable>, Context)
输入key类型，输入value类型

context.write(Text, IntWritable)
输出key类型，输出value类型

## MapReduce机制
- 角色：client, JobTracker, TaskTracker, HDFS
- Client: 提交job到JobTracker
- JobTracker: 分发任务给TaskTracker, 输入分片 Input Split
- TaskTracker: 执行小任务，每个分片一个map任务
- HDFS：存储数据

1. 输入分片（Input Split）：分片在在HDFS里
2. map：本地操作
3. shuffle: 排序，key相同的数据放在一起，分区 partitioner
4. reduce: 一个分区对应一个reduce

## map阶段
- map: 输入split，输出到内存缓冲区
- partition：在内存缓冲区优化，决定数据交给哪个reducer处理，负载均衡
- spill: sort & combine，优化，通常跟reducer是一样的，写磁盘
- merge: 磁盘可能有多个溢写文件，需要merge成一个？merge成group： key, list?

## reduce阶段
- fetch: 从不同的map拉取中间结果
- merge：合并成一个文件
- reduce：

## shuffe阶段
- partition: 根据key把k-v放入某个partition
- spill: 每个partition里的k-v进行排序，并且执行combine
- merge: 每个partition里的k-v写成group：k-list(?)
- fetch: 获取map的输出文件
- merge: 合并来自不同map的输出文件

### 分片 split
- 根据map task的数量来确定分片的数目，确定分片的大小

### 分区 partition
- 为了解决map的结果数据太大？交给多个reduce去处理？
- 同一个key的所有数据都写入同一个分区？不能分散到多个分区？
- 分区的数量默认由reducer确定，一个分区里包括多个key
- map会把输出数据写到多个分区文件？
- reduce去map拉取分区文件，然后处理？

### 归并排序
- 先把大数组分解成小数组
- 小数组两两排序

