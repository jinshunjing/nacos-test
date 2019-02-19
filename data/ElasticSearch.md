
## CentOS 7 安装 ElasticSerach 6.6.0
创建ES用户，这是因为Elastic不能以root的身份启动
`adduser elasticusr`

检查max file descriptors的配置是否大于等于65536
`ulimit -Hn`
也可以检查下列配置文件
`vi /etc/security/limits.conf`
`vi /etc/profile`
如果不大于65536，则修改配置，并执行
`source /etc/security/limits.conf`

检查vm.max_map_count的配置是否为262144
`sysctl -a|grep vm.max_map_count`
如果不是，则修改
`sysctl -w vm.max_map_count=262144`
或者修改文件
`vi /etc/sysctl.conf`
`vm.max_map_count=262144`

切换到ES用户并进入根目录
`su elasticusr`
`cd ~`

下载安装包并解压
`wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.6.0.tar.gz`
`tar -xzvf elasticsearch-6.6.0.tar.gz`

启动
`nohup ~/elasticsearch-6.6.0/bin/elasticsearch 1>~/elastic.log &`

验证
`curl localhost:9200`

允许远程访问
`vi ～/elasticsearch-6.6.0/config/elasticsearch.yml`
`network.host: 0.0.0.0`


## 原理
- 索引Index
- 类型Type
- 文档Document

- 倒排索引 Inverted index
- 分词Term
- Posting List
- Term Dictionary：Term排序，可以二分查找
- Term Index：Term Dictionary太大，不能全部放入内存。Term Index是前缀，是一颗trie树。通过Term index可以快速定位到Term dictionary的某个offset，然后顺序查找磁盘。
- 压缩技术: Lucene Finite State Transducers
- 为什么比MySQL快？因为Term Index减少了磁盘IO。

- 联合索引：两个结果集的合并，skip list跳表, bitset

https://www.infoq.cn/article/database-timestamp-02?utm_source=infoq



