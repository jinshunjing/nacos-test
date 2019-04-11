# Dubbo Wrapper

Dubbo Wrapper 可以认为是一种反射机制。它既可以读写目标实例的字段，也可以调用目标实例的方法。比如
- Car 是接口；RaceCar 是实现类，实现了 Car；ferrari 和 porsche 是 RaceCar 的实例
- 我们可以为接口 Car 生成一个 Warpper 子类，比如 Wrapper0；然后创建 Wrapper0 的实例 wrapper0
- 可以通过 wrapper0#setPropertyValue 来修改 ferrari 的字段，也可以修改 porsche 的字段
- 可以通过 wrapper0#invokeMethod 来调用 ferrari 的方法，也可以调用 porsche 的方法
- 优点：通过一个 Wrapper 实例就可以操作N个目标接口 Car 的实例

比如我们有一个Car接口，定义了3个方法：
```java
package org.apache.dubbo.common.bytecode;

public interface Car {
    String getBrand();
    long getWeight();
    void make(String brand, long weight);
}
```

Wrapper#makeWrapper之后生成的Wrapper子类代码如下：
```java
package org.apache.dubbo.common.bytecode;

public class Wrapper0 extends Wrapper {
    // 字段名列表
    public static String[] pns;

    // 字段名与字段类型的映射关系
    public static java.util.Map<String, Class<?>> pts;

    // 方法名列表
    public static String[] mns;

    // 声明的方法名列表
    public static String[] dmns;

    // 每个public方法的参数类型
    public static Class[] mts0;
    public static Class[] mts1;
    public static Class[] mts2;

    public String[] getPropertyNames() {
        return pns;
    }

    public boolean hasProperty(String n) {
        return pts.containsKey($1);
    }

    public Class getPropertyType(String n) {
        return (Class) pts.get($1);
    }

    public String[] getMethodNames() {
        return mns;
    }

    public String[] getDeclaredMethodNames() {
        return dmns;
    }

    public void setPropertyValue(Object o, String n, Object v) {
        org.apache.dubbo.common.bytecode.Car w;
        try {
            w = ((org.apache.dubbo.common.bytecode.Car) $1);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
        throw new org.apache.dubbo.common.bytecode.NoSuchPropertyException("Not found property \"" + $2 + "\" field or setter method in class org.apache.dubbo.common.bytecode.Car.");
    }

    public Object getPropertyValue(Object o, String n) {
        org.apache.dubbo.common.bytecode.Car w;
        try {
            w = ((org.apache.dubbo.common.bytecode.Car) $1);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
        if ($2.equals("brand")) {
            return ($w) w.getBrand();
        }
        if ($2.equals("weight")) {
            return ($w) w.getWeight();
        }
        throw new org.apache.dubbo.common.bytecode.NoSuchPropertyException("Not found property \"" + $2 + "\" field or setter method in class org.apache.dubbo.common.bytecode.Car.");
    }

    public Object invokeMethod(Object o, String n, Class[] p, Object[] v) throws java.lang.reflect.InvocationTargetException {
        org.apache.dubbo.common.bytecode.Car w;
        try {
            w = ((org.apache.dubbo.common.bytecode.Car) $1);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
        try {
            if ("make".equals($2) && $3.length == 2) {
                w.make((java.lang.String) $4[0], ((Number) $4[1]).longValue());
                return null;
            }
            if ("getBrand".equals($2) && $3.length == 0) {
                return ($w) w.getBrand();
            }
            if ("getWeight".equals($2) && $3.length == 0) {
                return ($w) w.getWeight();
            }
        } catch (Throwable e) {
            throw new java.lang.reflect.InvocationTargetException(e);
        }
        throw new org.apache.dubbo.common.bytecode.NoSuchMethodException("Not found method \"" + $2 + "\" in class org.apache.dubbo.common.bytecode.Car.");
    }
}
```