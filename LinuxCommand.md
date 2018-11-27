# Linux Command

## 设置环境变量
vi /etc/profile <br/>
PATH=/usr/local/bin:$PATH <br/>
export PATH <br/>
LIBRARY_PATH=/lib64:$LIBRARY_PATH <br/>
export LIBRARY_PATH
source /etc/profile

## find
find . -iname "key"

## awk
先按行读取文件，然后按域分隔符划分单词

### 指定域分隔符
awk -F ':' 'BEGIN {print "user,app"} {print $1","$5} END {print "end,end"}'

### 匹配关键字来过滤行
awk -F ':' '/root/ {print $1}' /etc/passwd

### 使用Hash
awk -F ':' '{users[$1]=$5} END {for (user in users) {print user","users[user]}}' /etc/passwd

### 批量修改文件后缀
ls | awk -F "." '{print "mv "$0" "$1".txt"}' | sh

## netstat
### Server Socket
netstat -ltnp

### Socket
netstat -tnp

## tmux
列表：tmux ls <br/>
进入：tmux at -t xxx <br/>
临时退出：ctr+b d

## ssh
### 文件的下载与上传
scp user@0.0.0.0:/usr/dir/file local <br/>
scp local user@0.0.0.0:/usr/dir/

rsync user@0.0.0.0:/usr/dir/file local <br/>
rsync local user@0.0.0.0:/usr/dir/





