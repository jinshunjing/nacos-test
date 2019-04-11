# Dubbo线程模型

## 原理
- 模块：dubbo-remoting-api
- SPI接口Dispatcher，提供方法dispatch(ChannelHandler)
- 默认采用AllDispatcher

## AllDispatcher
- 全部放入线程池

## DirectDispatcher
- 全部不放入线程池

## ConnectionOrderedDispatcher
- Connected/Disconnected放入有序队列，其他放入线程池

## MessageOnlyDispatcher
- 只有Request和Response事件放入线程池

## ExecutionDispatcher
- 只有Request事件放入线程池
