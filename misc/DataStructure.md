
## 二叉堆/二项堆
- 完全二叉树，只有叶子节点不满
- 用数组来存储：第一个叶子节点n>>>1；左儿子(i<<1)+1
- 堆序性：节点的值大于/小于子节点的值

### 堆操作
- 建堆：所有的叶子节点已经堆有序，从后往前逐个处理非叶子节点，使用shiftDown：如果大于两个子节点那么已经堆有序；否则跟数值大的子节点交换，重复此步骤直到堆有序。
- 删除根节点：把最后一个叶子节点放入根节点，然后shiftDown
- 添加新节点：添加到最后，然后shiftUp：如果大于父节点，则交换；重复此过程直到不需要交换。

### 堆排序
- 每次删除根节点，放到最后

### JDK
- PriorityQueue: 最小堆
- PriorityBlockingQueue: 线程安全，加了ReentrantLock与Condition

## 斐波那契堆


## 跳表

### JDK
- ConcurrentSkipListMap: 
- ConcurrentSkipListSet:


## 队列

### JDK
- Queue:
- LinkedList:
- ConcurrentLinkedQueue:

- BlockingQueue:
- ArrayBlockingQueue:
- LinkedBlockingQueue:
- PriorityBlockingQueue:
- SynchronousQueue:
- DelayQueue: 使用PriorityBlockingQueue
- LinkedTransferQueue: SynchronousQueue的升级版。transfer时如果有消费者则消费，没有则自旋等待。



