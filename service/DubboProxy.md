# Dubbo Proxy 原理与实例

Dubbo代理机制与JDK的代理机制不同。比如我们有一个接口Car，
- JDK通过 Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler) 创建Car的一个实例
Dubbo则是分成了两个步骤：
- 首先生成了Car的实现类proxy0。这是一个代理类，所有的方法都要通过InvocationHandler
- 然后生成了Proxy的子类Proxy0，并创建了实例。通过newInstance可以创建代理类proxy0的实例
- 优点：一个接口只需要生成一个Proxy实例，然后可以创建N个接口的实例

比如我们有几个接口Car:
```java
package org.apache.dubbo.common.bytecode;

public interface Car {
    String getBrand();
    long getWeight();
    void make(String brand, long weight);
}
```

Dubbo首先生成Car的一个实现类proxy0，每个方法都代理给InvocationHandler处理：
```java
package org.apache.dubbo.common.bytecode;

public class proxy0 implements org.apache.dubbo.common.bytecode.Car {
    // 接口定义的方法
    public static java.lang.reflect.Method[] methods;

    private java.lang.reflect.InvocationHandler handler;

    public proxy0() {
    }

    public proxy0(java.lang.reflect.InvocationHandler h) {
        handler=$1;
    }

    public void make(String brand, long weight) {
        Object[] args = new Object[2];
        args[0] = ($w) $1;
        args[1] = ($w) $2;
        Object ret = handler.invoke(this, methods[0], args);
    }

    public String getBrand() {
        Object[] args = new Object[0];
        Object ret = handler.invoke(this, methods[1], args);
        return (java.lang.String) ret;
    }

    public long getWeight() {
        Object[] args = new Object[0];
        Object ret = handler.invoke(this, methods[2], args);
        return ret == null ? (long) 0 : ((Long) ret).longValue();
    }
}
```

然后生成一个Proxy的子类Proxy0，可以用于创建代理类的实例：
```java
public class Proxy0 extends org.apache.dubbo.common.bytecode.Proxy {
    public Object newInstance(java.lang.reflect.InvocationHandler h) {
        return new proxy0($1);
    }
}
```
