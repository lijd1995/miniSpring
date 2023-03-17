# 手写 IoC 模块

## 一、通过 BeanFactory 实现原始版本的 IoC 容器

![](https://ljd-image-upload.oss-cn-beijing.aliyuncs.com/sources/202303171611668.jpeg)

我们定义了 BeanFactory 和 Resource 两个接口，分别用于实现 IoC 容器和资源加载。

接着对 BeanFactory 接口进行实现，定义了 SimpleBeanFactory 类，该类实现了 BeanFactory 接口，同时定义了一个 Map 用于存储 bean 的实例。

ClassPathResource 类实现了 Resource 接口，用于加载类路径下的资源文件。

XmlReader 类用于解析 xml 文件，将 xml 文件中的 bean 信息解析成 BeanDefinition 对象，然后将 BeanDefinition 对象存储到 SimpleBeanFactory 中。

最后，我们定义了一个测试类，用于测试我们的 IoC 容器。

## 二、扩展 Bean：如何配置 contructor、property 和 init-method

![](https://ljd-image-upload.oss-cn-beijing.aliyuncs.com/sources/202303171610527.jpeg)

### 计划

1. 增加单例 Bean 的接口定义，然后把所有的 Bean 默认为单例模式。
2. 预留事件监听的接口，方便后续进一步解耦代码逻辑。（这个正好学习一下 Java 的事件监听机制）
3. 扩展 BeanDefinition，添加一些属性，如：contructor、property 和 init-method。

### 实现

#### 1. 构建单例的 Bean

我们之前是在 SimpleBeanFactory 中对 BeanDefinition 和创建 Bean 都进行管理。
为了单例创建对象，首先创建一个 SingletonBeanRegistry 接口，用于管理单例 Bean。
DefaultSingletonBeanRegistry 类实现了 SingletonBeanRegistry 接口，用于管理单例 Bean。
SimpleBeanFactory 继承 DefaultSingletonBeanRegistry，所有的 Bean 操作都从 DefaultSingletonBeanRegistry 进行获取。

#### 2. 增加事件监听（这块是学习点）

构件好单例 Bean，为了监控容器的启动状态，需要增加事件监听。

定义了 ApplicationEvent 和 ApplicationEventPublisher，前者用户监听应用的时间，另一个是发布事件

ApplicationEvent 继承了 Java 工具包内的 EventObject，在 Java 的事件监听基础上进行了简单的封装。

目前没有任何实现，但为我们后续使用观察者模式解耦代码提供了入口。

#### 3. 为 Bean 注入属性做一些铺垫

Spring 有三种属性注入点方式：Field 注入、Setter 注入和构造器（Constructor）注入。

- Field 注入是指我们给 Bean 里面某个变量赋值
- Setter 注入是提供了一个 setter 方法，调用 setXXX() 来注入值
- constructor 就是在构造器 / 构造函数里传入参数来进行注入

先看一下 Setter 注入与构造器注入的两种方式

Setter 注入是通过 <property> 标签、构造器注入是通过 <constructor-arg> 标签。

```xml

<beans>
    <bean id="aservice" class="com.minis.test.AServiceImpl">
        <constructor-arg type="String" name="name" value="abc"/>
        <constructor-arg type="int" name="level" value="3"/>
        <property type="String" name="property1" value="Someone says"/>
        <property type="String" name="property2" value="Hello World!"/>
    </bean>
</beans>

```
定义 Argument 接收 <constructor-arg> 标签内容
定义 Property 接收 <property> 标签内容

再定义两个集合处理类 Arguments 和 Propertys 里面包含封装、增加、获取、判断等操作方法

接下来还需要做两件事：
1. 扩展 BeanDefinition 的属性
2. 扩展 BeanFactory 接口，增强对 Bean 的处理能力

扩展 BeanDefinition 属性：

1. 添加 scope 属性，用于区分单例和多例
2. 添加 lazyInit 属性，表示 bean 加载的时候是否要初始化，如果初始化，可以调用 initMethodName 进行初始化
3. 添加 dependsOn 属性，用于记录 Bean 之间的依赖关系
4. 添加 ArgumentValues 和 PropertyValues 用户存储 Bean 的构造器注入和 Setter 注入的属性

# 3. 依赖注入：如何给Bean注入值并解决循环依赖问题？

没有必要去理解三级缓存，因为三级缓存不是必要条件，明白这个道理就好。
就像我一个数组也可以处理，两个数组也可以处理，只需要知道就好。


