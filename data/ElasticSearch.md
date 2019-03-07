

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



