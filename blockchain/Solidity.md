

## 主要概念
* contract: 
* state variable:

## Type

### 基本类型
* bool, int, uint
* bytes, string
* address
* function
* contract

### Arrays
* 赋值的时候，到底是引用还是拷贝
* Fix-sized Arrays
* Dynamically-sized Arrays

### Structs
* 赋值的时候，到底是引用还是拷贝


## Function
* function 函数名(参数列表) [Modifier] [Visibility] [State Mutability] returns(返回值列表) {}

### Function Modifier
* _; 表示执行的函数
* 可以传入参数

### Function Visibility Specifier
* external: only visible externally. Message call, memory is cleared and copied. this.f() is an external call.
* internal: only visible in the same contract and the derived contracts. Jumps, memory is not cleared.
* public: visible externaly and internally. 
* private: only visible in the current contract, not any derived contracts.

### Function State Mutability
* pure: cannot access state, use staticcall
* view: cannot modify state, use staticcall
* payable: can receive ether

### Payable Function
* 可以转移ETH与gas给被调用的合约：f.value(x).gas(y)

### Fullback Function
* function () external payable {}
* 在找不到函数时执行


## State Variables

### State Visibility Specifier
* public: has a default getter which is external. Array getter is array(uint).

### Constant State Variables
* constant: 只能赋值一次


## Events
* topics: 通过indexed来标识，是hash值
* data part of the log
* 通过log实现
* logi：第一个参数是data, 第二个参数是event名称的hash，其余是topics
* 用于实现event
* 合约不能访问log

## Tuple
* (uint8 x, uint8 y) = (1, 2)
* (x, y) = (1, 2)


## Error Handling
* 没有异常处理，所以调用完成之后需要检查结果是否正确
* assert: check invariants. assert fail should not happen, must be a bug. invalid operation, consumes all gas.
* require: check inputs, states, return values. can provide a message. revert operation, consumes no gas.
* revert: flag an error and revert the current call. can be replaced by require.


## 架构

### 继承
* is
* 继承多个contract时，从右到左的解析父类
* constructor 参数可以直接给出：B is A(1) 或者用modifier: constructor(uint i) A(i)
* abstract contract: 至少一个函数没有实现代码

### 接口
* interface
* 不能继承
* 不能有state variables
* 所有的function必须是external

### 库 Lirary
* 直接调用时不能修改state, delegatecall时可以读写调用者的state
* 通过delegatecall，代码在调用合约的context运行
* 一次部署，多次使用，在编译时指定library的地址






