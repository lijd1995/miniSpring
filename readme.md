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

在 XmlBeanDefinitionReader 中对构造器的 <constructor-arg> 和 <property> 标签进行解析放入到 BeanDefinition 中

在 SimpleBeanFactory 中对 BeanDefinition 进行处理，通过对 ArgumentValues 和 PropertyValues 解析将构造器注入和 Setter 注入的属性进行注入。

为了解决循环依赖，将创建bean分为三步：
getBean() -> createBean() -> doCreateBean()

getBean() 方法是对外暴露的方法，用于获取 Bean，该方法会先从毛坯中 earlySingletonObjects 中获取 Bean，如果没有的话，调用 createBean() 方法创建 Bean。
createBean() 方法会调用 doCreateBean() 方法来进行 Bean 的创建，然后放入到 earlySingletonObjects 毛坯中，调用 handleProperties 方法将属性给补齐。
doCreateBean() 方法会进行 Bean 的创建，主要是调用构造器来创建毛坯实例。

这里面还需要学习一点，关于反射如何使用的，比如如何创建构造器、如何注入属性等。

## 4. 增强IoC容器：如何让我们的Spring支持注解

首先修改一下目录结构，将一些内容移动到 factory 文件夹下，这个跟 Spring 中的结构是对应的。

**因此这块应该对照着 Spring 的源码去分析一下这块的内容。**

```json

factory —— BeanFactory.java
factory.xml —— XmlBeanDefinitionReader.java
factory.support —— DefaultSingletonBeanRegistry.java、
BeanDefinitionRegistry.java、SimpleBeanFactory.java
factory.config —— SingletonBeanRegistry.java、ConstructorArgumentValues.java、
ConstructorArgumentValue.java、BeanDefinition.java

// 注：
// ConstructorArgumentValues由ArgumentValues改名而来
// ConstructorArgumentValue由ArgumentValue改名而来

```

Autowired 的处理过程如下：
1. 定义Autowired注解：使用 AutoWired 的好处就是不用显式地在 XML 配置文件中使用 ref 属性指定依赖的对象。

但是注解也是需要代码解释的，必须要有另外一个程序来解释它。 

那我们自问一下：我们要在哪一段程序来解释这个注解呢？肯定是要有创建好的对象，就是在 createBean 方法之后, 也就是在 getBean 方法中在加上下面内容。

在 createBean 之后留了几个方法，分别是 postProcessBeforeInitialization、init-method 和 postProcessAfterInitialization，这三个方法都是在 Bean 初始化之后调用的。

2. 定义 BeanPostProcessor 接口，包括 postProcessBeforeInitialization 和 postProcessAfterInitialization 两个方法

3. 定义AutowiredAnnotationBeanPostProcessor类实现 BeanPostProcessor 接口，对 Autowired 注解进行处理。
   1. 获取 Bean 的所有属性，然后遍历属性，判断是否有 Autowired 注解，如果有的话，就通过 BeanFactory 来获取 Bean，然后将 Bean 注入到属性中。
   2. 这里用到的 BeanFactory 是 AutowiredBeanFactory，专门为 Autowired 注入 Bean 准备的。
   
4. 我们之前有 SimpleBeanFactory，现在又需要一个 AutowiredBeanFactory，这两个接口有重复的逻辑，所以抽象一个 AbstractBeanFactory 类，里面实现通用逻辑，也就是模板方法。
   1. 将 refresh()、getBean()、registerBeanDefinition() 等方法抽取到抽象类中。
   2. getBean() 中是一个模板方法，里面定义了 applyBeanPostProcessorsBeforeInitialization 和 applyBeanPostProcessorsAfterInitialization 两个抽象方法。
   
5. AutowireCapableBeanFactory 继承了 AbstractBeanFactory，实现了里面的两个抽象方法。
   1. 这里面涉及到 BeanPostProcessor 的概念，也就是我们在第三步定义的AutowiredAnnotationBeanPostProcessor类
   2. 通过 addBeanPostProcessor 将 BeanPostProcessor 注册到 beanPostProcessors 中。
   3. applyBeanPostProcessorsBeforeInitialization 方法中，遍历 beanPostProcessor，然后将 this 作为 BeanFactory 注入进去，之后调用 postProcessBeforeInitialization 方法，对 Bean 进行处理。
   4. applyBeanPostProcessorsAfterInitialization 方法中，遍历 beanPostProcessor，然后将 this 作为 BeanFactory 注入进去，之后调用 postProcessAfterInitialization 方法，对 Bean 进行处理。
   5. 这块不好理解，我们梳理一下（先将Processor注入到BeanFactory中，使用Processor的时候又将BeanFactory作为this注入到Processor中，而这种解耦是通过接口来实现的）

6. 调整 ClassPathXmlApplicationContext，之前是 SimpleBeanFactory，现在改为 AutowireCapableBeanFactory。

7. 在 ClassPathXmlApplicationContext 中，调用 refresh() 方法。
   1. 里面先调用了 registerBeanPostProcessors() 方法，将 BeanPostProcessor 注册到 BeanFactory 的 beanPostProcessors 中。
   2. 调用 BeanFactory 的 refresh 方法，进行实体的构建流程。
   
总结一下，相较于之前来说，我们想要对 Autowired 进行处理，需要在创建完 Bean 之后调用 applyBeanPostProcessorsBeforeInitialization 和 applyBeanPostProcessorsAfterInitialization 方法，这两个方法中会调用 BeanPostProcessor 的 postProcessBeforeInitialization 和 postProcessAfterInitialization 方法，
这两个方法中会调用 AutowiredAnnotationBeanPostProcessor 的 postProcessBeforeInitialization 和 postProcessAfterInitialization 方法，这两个方法中会调用 AutowiredAnnotationBeanPostProcessor 的 processInjection 方法。

AutowiredAnnotationBeanPostProcessor 中肯定也需要用到 BeanFactory 进行对象的创建，这个时候需要创建一个对应的 BeanFactory，就是 AutowireCapableBeanFactory。

AutowireCapableBeanFactory 和 SimpleBeanFactory 有类似的结构，就将相同的逻辑也抽象出来，一个 AbstractBeanFactory。这个抽象类定义了两个抽象方法，也就是 applyBeanPostProcessorsBeforeInitialization 和 applyBeanPostProcessorsAfterInitialization 方法

这两个抽象方法需要交给 AutowireCapableBeanFactory 来实现，然后实现肯定要用到 BeanProcessor，所以需要有一个列表来保存。

Spring 巧妙之处就在于通过接口进行解耦，也通过接口进行扩展，留出一个列表，可以循环遍历接口进行操作。

## 05｜实现完整的IoC容器：构建工厂体系并添加容器事件

前面 IoC 核心部分和骨架都有了，怎么让这个 IoC 丰满起来呢？

需要实现更多的功能，让 IoC 更加完备。

这一部分要实现一个完整的 IoC 容器，包括：

1. 进一步增加扩展性，
增加四个接口：ListableBeanFactory、ConfigurableBeanFactory、ConfigurableListableBeanFactory、EnvironmentCapable

ListableBeanFactory：是将 Bean 作为一个集合，该接口提供一些方法，获取 Bean 的一些属性。比如获取 Bean 的数量，得到所有 Bean 的名字，
按照某个类型获取 Bean 列表等。

ConfigurableBeanFactory 接口：继承了 BeanFactory 和 SingletonBeanRegistry 接口。
在该接口中维护 Bean 之间的依赖关系以及支持 BeanProcessor 的注册

ConfigurableListableBeanFactory 接口：继承了 ConfigurableBeanFactory 、 ListableBeanFactory、AutowireCapableBeanFactory 接口。
是一个接口的合并汇总

上面这三个接口是给通用的 BeanFactory 和 BeanDefinition 进行扩展，增加了很多个处理方法，增强了各种特性。

接口隔离原则：在 Java 语言的设计中，一个 Interface 代表的是一种特性或者能力，我们把这些特性或能力一个个抽取出来，各自独立互不干扰。如果一个具体的类，想具备某些特性或者能力，就去实现这些 interface，随意组合。
Spring 框架中用的很多，通过接口进行隔离。增加系统的可扩展性。

我们之前的 AutowireCapableBeanFactory 是一个实现类，继承了 AbstractBeanFactory。
现在要把 AutowireCapableBeanFactory 该成接口，然后创建新的抽象类 AbstractAutowireCapableBeanFactory 代替原有的实现类。

---

2. 增加环境因素

前面是扩充 BeanFactory 体系，我们可以给容器增加一些环境因素，使得容器整体的属性有地方存储。

在 core 目录下新建 env 目录，增加 PropertyResolver.java、EnvironmentCapable.java、Environment.java 三个接口类。EnvironmentCapable 主要用于获取 Environment 实例，Environment 则继承 PropertyResoulver 接口，用于获取属性。所有的 ApplicationContext 都实现了 Environment 接口


3. 实现 DefaultListableBeanFactory，该类是 Spring IoC 的引擎

DefaultListableBeanFactory 继承了 AbstractAutowireCapableBeanFactory,并实现了 ConfigurableListableBeanFactory 接口。

实现后我们看不出它怎么成为 IoC 引擎的，它成为引擎的秘诀在于它继承了其他 BeanFactory 类来实现 Bean 的创建管理能力。

AbstractAutowiredCapableBeanFactory 是对 Bean 进行创建。
ConfigurableListableBeanFactory 是增加了对 Bean 的管理能力。
DefaultListableBeanFactory 实现了 ConfigurableListableBeanFactory 的方法，而这些方法里面用到的 Bean 是在 AbstractAutowiredCapableBeanFactory 中提供的方法。

看一下 Spring 的继承架构图

![](https://ljd-image-upload.oss-cn-beijing.aliyuncs.com/sources/202304041729776.png)

MiniSpring 和 Spring 框架设计得几乎是一样的。这样就可以理解了 SpringIoC的核心流程。

然后我们修改一下 ClassPathXmlApplicationContext，让这个启动类注入 DefaultListableBeanFactory。

这样整个框架完成内部逻辑的闭环流程。

5. 改造 ApplicationContext
