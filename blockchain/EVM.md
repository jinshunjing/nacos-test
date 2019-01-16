

## Ethereum World State
* balance, nonce
* code, storage


## 合约编译与部署
* deployment bytecode: runtime bytecode + initialization
* runtime bytecode


## EVM执行
* gas: miner fee; refunded
* dispatcher: check length > 4; switch by function prefix;
* update world state
* call other contracts


## 图灵完备
* 一切可计算的问题都能计算
* 停机问题
* 执行其他程序


## EVM Data Area 数据区域
* state
* code
* storage 256-256，持久存储，合约私有
* memory 读256，写8/256，每次消息调用使用，请求新空间需要消耗gas
* stack 1024x256，计算空间
* calldata

### Data Location
* 引用类型的变量需要指定数据位置
* calldata: 只能应用于external的函数
* storage: 
* memory: 只生存在一次消息调用

### Stack
* 1024 slots


## Message Call 消息调用
* 合约可以调用其他合约
* 被调用合约会收到一块memory，以及calldata，返回的数据会存储在调用合约的memory

