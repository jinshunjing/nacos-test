# Dubbo Filter 原理

Dubbo的Filter职责链有点绕：
- 在Invoker#invoke方法里调用Filter#invoke
- 在Filer#invoke方法的最后一步调用下一个Invoker的inovke方法
- 如此递归调用
- 在Invoker#invoke的最后一步调用Filter#onResponse
- 如此递归返回

Fitler SPI 定义了两个方法：
```java
package org.apache.dubbo.rpc;

@SPI
public interface Filter {
	Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException;
	
	default Result onResponse(Result result, Invoker<?> invoker, Invocation invocation) {
        return result;
    }
}
```

Filter 的一个实现类：
```java
package org.apache.dubbo.rpc.filter;

/**
 * 记录Provider超时的调用，用于监控性能太差的服务
 */
@Activate(group = Constants.PROVIDER)
public class TimeoutFilter implements Filter {

    private static final String TIMEOUT_FILTER_START_TIME = "timeout_filter_start_time";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 记录开始时间，放入invocation的attachment里
        if (invocation.getAttachments() != null) {
            long start = System.currentTimeMillis();
            invocation.getAttachments().put(TIMEOUT_FILTER_START_TIME, String.valueOf(start));
        } else {
            if (invocation instanceof RpcInvocation) {
                RpcInvocation invc = (RpcInvocation) invocation;
                long start = System.currentTimeMillis();
                invc.setAttachment(TIMEOUT_FILTER_START_TIME, String.valueOf(start));
            }
        }
        // 下一个Filter
        return invoker.invoke(invocation);
    }

    @Override
    public Result onResponse(Result result, Invoker<?> invoker, Invocation invocation) {
        // 获取开始时间
        String startAttach = invocation.getAttachment(TIMEOUT_FILTER_START_TIME);
        if (startAttach != null) {
            // 调用服务的耗时
            long elapsed = System.currentTimeMillis() - Long.valueOf(startAttach);
            // 调用耗时超过了设置的超时
            if (invoker.getUrl() != null
                    && elapsed > invoker.getUrl().getMethodParameter(invocation.getMethodName(),
                    "timeout", Integer.MAX_VALUE)) {
                if (logger.isWarnEnabled()) {
                    logger.warn("invoke time out. method: " + invocation.getMethodName()
                            + " arguments: " + Arrays.toString(invocation.getArguments()) + " , url is "
                            + invoker.getUrl() + ", invoke elapsed " + elapsed + " ms.");
                }
            }
        }
        return result;
    }
}
```

Filter职责链在 ProtocolFilterWrapper 里创建（虽然这里创建的是Invoker职责链）：
```java
package org.apache.dubbo.rpc.protocol;

public class ProtocolFilterWrapper implements Protocol {
    /**
     * 创建Invoker职责链，也就是Filter职责链
     */
    private static <T> Invoker<T> buildInvokerChain(final Invoker<T> invoker, String key, String group) {
        // 职责链的最后一个是传入的Invoker
        Invoker<T> last = invoker;
        // 获取激活的Filter列表
        List<Filter> filters = ExtensionLoader.getExtensionLoader(Filter.class)
                .getActivateExtension(invoker.getUrl(), key, group);
        if (!filters.isEmpty()) {
            // 从尾到头创建Invoker职责链，这样返回的就是职责链上的第一个Invoker
            for (int i = filters.size() - 1; i >= 0; i--) {
                final Filter filter = filters.get(i);
                // 当前Invoker的下一个Invoker是last
                final Invoker<T> next = last;
                // last变成当前Invoker
                // 通过Filter创建Invoker
                last = new Invoker<T>() {
                    /**
                     * 先执行Filter#invoke(next, invocation)
                     *     最后一步执行next#invoke(invocation)，这样可以走到下一个Filter
                     * 然后执行Filter#onResponse(result, invoker, invocation)
                     */
                    @Override
                    public Result invoke(Invocation invocation) throws RpcException {
                        // 执行Filter#invoke，指向下一个Invoker
                        Result result = filter.invoke(next, invocation);

                        // 如果是异步请求，执行Filter#onResponse
                        if (result instanceof AsyncRpcResult) {
                            AsyncRpcResult asyncResult = (AsyncRpcResult) result;
                            asyncResult.thenApplyWithContext(r -> filter.onResponse(r, invoker, invocation));
                            return asyncResult;
                        }
                        // 如果是同步请求，执行Filter#onResponse
                        // 这里的invoker为什么不是next?
                        else {
                            return filter.onResponse(result, invoker, invocation);
                        }
                    }

                    @Override
                    public Class<T> getInterface() {
                        return invoker.getInterface();
                    }
                    @Override
                    public URL getUrl() {
                        return invoker.getUrl();
                    }
                    @Override
                    public boolean isAvailable() {
                        return invoker.isAvailable();
                    }
                    @Override
                    public void destroy() {
                        invoker.destroy();
                    }
                    @Override
                    public String toString() {
                        return invoker.toString();
                    }
                };
            }
        }
        return last;
    }
}
```

谢谢阅读！
