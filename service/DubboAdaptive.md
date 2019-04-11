# Dubbo 自适应拓展机制原理与实例

Dubbo的拓展类(Extension)是通过SPI机制加载的：
- 对于某个SPI接口，加载指定目录下(META-INF/dubbo)名称为SPI接口全限名的配置文件
- 对于配置文件里的每一行键值对，加载SPI接口的实现类，也就是拓展类，然后创建实例

有时候我们不希望在Dubbo启动阶段就加载所有的拓展类，而是希望在用到某个拓展类时才加载，这就需要借助于自适应拓展机制。
- 对于某个SPI接口Car，生成一个自适应拓展类Car$Adaptive，并创建实例
- 调用该实例的方法时(SPI接口中包含Adaptive注解)，会加载拓展类并创建拓展实例，然后调用它的方法


比如我们定义了一个SPI接口：
```java
package org.apache.dubbo.common.extension;

import org.apache.dubbo.common.URL;

@SPI
public interface Car {
    String getBrand();
    long getWeight();
    @Adaptive
    void make(URL url, String brand, long weight);
}
```

Dubbo生成的自适应拓展类如下：
```java
package org.apache.dubbo.common.extension;

import org.apache.dubbo.common.extension.ExtensionLoader;

public class Car$Adaptive implements org.apache.dubbo.common.extension.Car {
    // 自适应方法
    public void make(org.apache.dubbo.common.URL arg0, java.lang.String arg1, long arg2) {
        if (arg0 == null) throw new IllegalArgumentException("url == null");
        // 查询URL里配置的属性，属性名是接口的小写，默认值在AdaptiveClassCodeGenerator里指定
        // 比如：new AdaptiveClassCodeGenerator(Car.class, "ferrari")
        org.apache.dubbo.common.URL url = arg0;
        String extName = url.getParameter("car", "ferrari");
        if (extName == null)
            throw new IllegalStateException(
                    "Failed to get extension (org.apache.dubbo.common.extension.Car) name from url ("
                            + url.toString() + ") use keys([car])");
        // 获取拓展实例
        org.apache.dubbo.common.extension.Car extension = (org.apache.dubbo.common.extension.Car)
                ExtensionLoader.getExtensionLoader(org.apache.dubbo.common.extension.Car.class)
                        .getExtension(extName);
        // 调用拓展实例的方法
        extension.make(arg0, arg1, arg2);
    }

    // 不支持非自适应方法
    public long getWeight() {
        throw new UnsupportedOperationException(
                "The method public abstract long org.apache.dubbo.common.extension.Car.getWeight() " +
                        "of interface org.apache.dubbo.common.extension.Car is not adaptive method!");
    }

    // 不支持非自适应方法
    public java.lang.String getBrand() {
        throw new UnsupportedOperationException(
                "The method public abstract java.lang.String org.apache.dubbo.common.extension.Car.getBrand() " +
                        "of interface org.apache.dubbo.common.extension.Car is not adaptive method!");
    }
}
```
