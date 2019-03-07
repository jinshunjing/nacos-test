
# HBase

## 概念

### HBase vs HDFS
* HBase是分布式数据库，HDFS是分布式文件系统


## 数据模型
* RowKey: 一行的主键
* Region: 横向切割，Key range
* Column Family: 纵向切割
* KeyValue: 丰富的自我描述信息

## 集群
* ZooKeeper
* NameNode: HDFS的元数据
* DataNode：HDFS的数据服务节点
* RegionServer：HBase的数据服务节点
* Master：HBase的管理节点



