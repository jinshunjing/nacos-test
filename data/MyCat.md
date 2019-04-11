
## 表
- 分片表
- 非分片表
- ER表： ER关系的子表与分表在同一个分片，join不需要跨库
- 全局表：配置表，冗余在所有的库中，join不需要跨库

## 节点
- 分片节点dataNode:
- 节点主机dataHost:

# CentOS 7 安装 MyCat 1.6.5
MySQL忽略表名大小写
```shell
vi /etc/my.cnf
lower_case_table_names=1
```
重启MySQL


创建Mycat用户组
```shell
groupadd mycat
useradd -g mycat mycat -d /home/mycat
passwd mycat
```

下载安装包
```shell
wget http://dl.mycat.io/1.6.5/Mycat-server-1.6.5-release-20180122220033-linux.tar.gz
```

安装
```shell
cd /usr/local
tar zxvf /mnt/Mycat-server-1.6.5-release-20180122220033-linux.tar.gz
chown -R mycat:mycat mycat/
```

设置环境变量
```shell
vi /etc/profile
export MYCAT_HOME=/usr/local/mycat
export PATH=$PATH:/usr/local/mycat/bin
source /etc/profile
```

配置文件
`conf/server.xml`
`conf/schema.xml`
`conf/rule.xml`

启动
`mycat start`




