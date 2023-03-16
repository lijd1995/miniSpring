# 手写 IoC 模块

## 一、通过 BeanFactory 实现原始版本的 IoC 容器

我们定义了 BeanFactory 和 Resource 两个接口，分别用于实现 IoC 容器和资源加载。

接着对 BeanFactory 接口进行实现，定义了 SimpleBeanFactory 类，该类实现了 BeanFactory 接口，同时定义了一个 Map 用于存储 bean 的实例。

ClassPathResource 类实现了 Resource 接口，用于加载类路径下的资源文件。

XmlReader 类用于解析 xml 文件，将 xml 文件中的 bean 信息解析成 BeanDefinition 对象，然后将 BeanDefinition 对象存储到 SimpleBeanFactory 中。

最后，我们定义了一个测试类，用于测试我们的 IoC 容器。

## 二、扩展 Bean：如何配置 contructor、property 和 init-method

