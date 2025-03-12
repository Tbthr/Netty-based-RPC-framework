<h2 id="f6Ief">概述</h2>

基于 Zookeeper、Netty、Kyro 等技术实现一个 RPC 框架，**<u>使得当调用远程方法时就如同调用本地方法一样简单，无需关注远程方法调用的底层细节</u>**，如：网络传输、序列化与反序列化等。



该框架主要包含以下几个部分：（更完善的一点的 RPC 框架可能还有监控模块）

1. **注册中心** ：负责服务地址的注册与查找，相当于目录服务。
2. **网络传输** ：发送网络请求来传递目标类和方法的信息以及方法的参数等数据到服务提供端。
3. **序列化和反序列化** ：要在网络传输数据就要涉及到**序列化**。
4. **动态代理** ：屏蔽远程方法调用的底层细节。
5. **负载均衡** ：避免单个服务器响应同一请求，容易造成服务器宕机、崩溃等问题。
6. **传输协议** ：客户端（服务消费方）和服务端（服务提供方）交流的基础。



<h2 id="9890f816">架构设计</h2>


![](https://tbthr-blog-images.oss-cn-hongkong.aliyuncs.com/images/6549198c8420804dd9c477569fcb8eec.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_15%2Ctext_c3Vubnk%3D%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_15%2Ctext_c3Vubnk%3D%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



**服务提供端 Server 向注册中心注册服务，服务消费者 Client 通过注册中心拿到服务相关信息，然后再通过网络请求服务提供端 Server。**



<h2 id="5c953913">远程调用过程</h2>


![](https://tbthr-blog-images.oss-cn-hongkong.aliyuncs.com/images/202202271315133.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_14%2Ctext_c3Vubnk%3D%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_14%2Ctext_c3Vubnk%3D%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



1. 服务消费端（client）以本地调用的方式调用远程服务；
2. 客户端 Stub（client stub） 接收到调用后负责将方法、参数等组装成能够进行网络传输的消息体（序列化）：RpcRequest；
3. 客户端 Stub（client stub） 找到远程服务的地址，并将消息发送到服务提供端；
4. 服务端 Stub（桩）收到消息将消息反序列化为Java对象: RpcRequest；
5. 服务端 Stub（桩）根据RpcRequest中的类、方法、方法参数等信息调用本地的方法；
6. 服务端 Stub（桩）得到方法执行结果并将组装成能够进行网络传输的消息体：RpcResponse（序列化）发送至消费方；
7. 客户端 Stub（client stub）接收到消息并将消息反序列化为Java对象: RpcResponse ，这样也就得到了最终结果。



<h2 id="dc25dee4">核心模块</h2>

![](https://cdn.nlark.com/yuque/0/2024/png/330006/1734427026946-6c56685b-f825-479d-bdcd-916543ad85a9.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_c3Vubnk%3D%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

<h3 id="67bb70bb">1. 注册中心</h3>

注册中心首先是要有的。比较推荐使用 Zookeeper 作为注册中心。当然了，你也可以使用 Nacos ，甚至是 Redis。



ZooKeeper 为我们提供了高可用、高性能、稳定的分布式数据一致性解决方案，通常被用于实现诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知、集群管理、Master 选举、分布式锁和分布式队列等功能。并且，ZooKeeper 将数据保存在内存中，性能是非常棒的。 在“读”多于“写”的应用程序中尤其地高性能，因为“写”会导致所有的服务器间同步状态。（“读”多于“写”是协调服务的典型场景）。



**注册中心负责服务地址的注册与查找，相当于目录服务。** 服务端启动的时候将服务名称及其对应的地址(ip+port)注册到注册中心，服务消费端根据服务名称找到对应的服务地址。有了服务地址之后，服务消费端就可以通过网络请求服务端了。



我们再来结合 Dubbo 的架构图来理解一下！

![](https://cdn.nlark.com/yuque/0/2024/jpeg/330006/1734427252495-7d30f576-b2c3-4e37-8230-375ce74da4a9.jpeg?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_13%2Ctext_c3Vubnk%3D%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

上述节点简单说明：

+ **Provider：** 暴露服务的服务提供方
+ **Consumer：** 调用远程服务的服务消费方
+ **Registry：** 服务注册与发现的注册中心
+ **Monitor：** 统计服务的调用次数和调用时间的监控中心
+ **Container：** 服务运行容器



调用关系说明：

1. 服务容器负责启动，加载，运行服务提供者。
2. 服务提供者在启动时，向注册中心注册自己提供的服务。
3. 服务消费者在启动时，向注册中心订阅自己所需的服务。
4. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
5. 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
6. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。



本项目运行后，会注册 2 个服务到 Zookeeper，如下所示：

![](https://cdn.nlark.com/yuque/0/2025/png/330006/1741533308941-e9ddf373-d833-4174-aa99-e353c1e9bd2d.png)



> + [ZooKeeper相关概念总结(入门)](https://javaguide.cn/distributed-system/distributed-process-coordination/zookeeper/zookeeper-intro.html)
> + [ZooKeeper相关概念总结(进阶)](https://javaguide.cn/distributed-system/distributed-process-coordination/zookeeper/zookeeper-plus.html)



<h3 id="c3e85192">2. 网络传输</h3>

**既然我们要调用远程的方法，就要发送网络请求来传递目标类和方法的信息以及方法的参数等数据到服务提供端。**



网络传输具体实现你可以使用 **Socket** （ Java 中最原始、最基础的网络通信方式。但是，Socket 是阻塞 IO、性能低并且功能单一）。


你也可以使用同步非阻塞的 I/O 模型 NIO ，但是用它来进行网络编程真的太麻烦了。不过没关系，你可以使用基于 NIO 的网络编程框架 Netty ：

1. Netty 是一个基于 NIO 的 client-server(客户端服务器)框架，使用它可以快速简单地开发网络应用程序。
2. 它极大地简化了 TCP 和 UDP 套接字服务器等网络编程，并且性能以及安全性等很多方面甚至都要更好。
3. 支持多种协议如 FTP，SMTP，HTTP 以及各种二进制和基于文本的传统协议。



<h3 id="83a5872a">3. 序列化与反序列化</h3>

网络传输的数据必须是二进制的，我们的 Java 对象没办法直接在网络中传输。为了能够让 Java 对象在网络中传输，我们需要将其**序列化**为二进制的数据。

我们最终需要的还是目标 Java 对象，因此我们还要将二进制的数据“解析”为目标 Java 对象，也就是对二进制数据进行一次**反序列化**。



另外，不仅网络传输的时候需要用到序列化和反序列化，将对象存储到文件、数据库等场景都需要用到序列化和反序列化。

![](https://tbthr-blog-images.oss-cn-hongkong.aliyuncs.com/images/a916173bd6f8f40727755e7dfbbea9bb.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_19%2Ctext_c3Vubnk%3D%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_19%2Ctext_c3Vubnk%3D%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

JDK 自带的序列化，只需实现 `java.io.Serializable`接口即可，不过这种方式不推荐，因为不支持跨语言调用，性能比较差，存在安全问题。



现在比较常用序列化的有 **hessian**、**kryo**、**protostuff** ......



<h3 id="b13b5e8f">4. 动态代理</h3>

> [静态代理和JDK、Cglib动态代理](https://www.yuque.com/u179863/coding/uwvep3y2hnbgirac)

RPC 的主要目的就是让我们调用远程方法像调用本地方法一样简单，我们**不需要关心远程方法调用的细节，**比如网络传输等。



**怎样才能屏蔽远程方法调用的底层细节呢？**



答案就是**动态代理**。简单来说，当你调用远程方法的时候，实际会通过代理对象来传输网络请求，不然的话，怎么可能直接就调用到远程方法。



当我们去调用一个远程的方法的时候，实际上是通过代理对象调用的。

网络传输细节都被封装在了  `invoke()`  方法中。

```java
@Slf4j
public class RpcClientProxy implements InvocationHandler {
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.info("invoked method: [{}]", method.getName());
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .group(rpcServiceProperties.getGroup())
                .version(rpcServiceProperties.getVersion())
                .build();
        RpcResponse<Object> rpcResponse = null;
        if (rpcRequestTransport instanceof NettyRpcClient) {
            CompletableFuture<RpcResponse<Object>> completableFuture = 
                (CompletableFuture<RpcResponse<Object>>) rpcRequestTransport.sendRpcRequest(rpcRequest);
            rpcResponse = completableFuture.get();
        }
        if (rpcRequestTransport instanceof SocketRpcClient) {
            rpcResponse = (RpcResponse<Object>) rpcRequestTransport.sendRpcRequest(rpcRequest);
        }
        this.check(rpcResponse, rpcRequest);
        return rpcResponse.getData();
    }
}
```



<h3 id="6fd2c5a0">5. 负载均衡</h3>

> [Dubbo中的负载均衡](https://www.yuque.com/u179863/coding/ua2ovc0vihqcu13i)

负载均衡也是需要的，为了避免单点故障。



<h3 id="6e3e8217">6. 传输协议</h3>

需要设计一个私有的 RPC 协议，这个协议是客户端（服务消费方）和服务端（服务提供方）交流的基础。



**通过设计协议，我们需要定义传输哪些类型的数据， 并且还会规定每一种类型的数据应该占多少字节。这样我们在接收到二进制数据之后，就可以正确的解析出我们需要的数据。**



通常，标准的 RPC 协议包含下面这些内容：

1. **魔数**：通常是 4 个字节。这个魔数主要是为了筛选来到服务端的数据包，有了这个魔数之后，服务端首先取出前面四个字节进行比对，能够在第一时间识别出这个数据包是否遵循自定义协议，为了安全考虑可以直接关闭连接以节省资源。
2. **序列化器编号**：标识序列化的方式，比如是使用 Java 自带的序列化，还是 json，kyro 等序列化方式。
3. **消息体长度**：运行时计算出来。
4. ......



我们定义的 RPC 协议如下：

```plain
 0     1     2     3     4       5     6    7    8     9          10      11       12    13    14   15  16
 +-----+-----+-----+-----+-------+-----+----+----+-----+-----------+-------+--------+-----+-----+---+----+
 |     magic   code      |version|     full length     |messageType| codec |compress|     RequestId      |
 +-----------------------+-------+---------------------+-----------+-------+--------+--------------------+
 |                                        ...body...                                                     |
 +-------------------------------------------------------------------------------------------------------+
 4B  magic code（魔法数）   1B version（版本）       4B full length（消息长度）    1B messageType（消息类型）
 1B codec（序列化类型）     1B compress（压缩类型）   4B  requestId（请求的Id）     ?B body（object类型数据）
```



<h3 id="ynmQt">7. 自定义注解与服务自动配置</h3>

我们定义了一个自定义注解，在启动类上配置后，即可实现服务的自动注入：

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegistrar.class)
@Documented
public @interface RpcScan {
    // 需要扫描的路径(默认是启动类所在路径)，该路径下所有的服务(@RpcService 修饰的类)都会被注入到 Spring 容器中
    String[] basePackage();
}
```

服务的自动注册到 Zookeeper 是由 BeanPostProcessor 实现的：

```java
@Override
public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (bean.getClass().isAnnotationPresent(RpcService.class)) {
        log.info("[{}] is annotated with  [{}]", bean.getClass().getName(), RpcService.class.getCanonicalName());
        // get RpcService annotation
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        // build RpcServiceProperties
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group(rpcService.group())
                .version(rpcService.version())
                .service(bean).build();
        // register service
        serviceProvider.publishService(rpcServiceConfig);
    }
    return bean;
}
```

<h3 id="A1ZuE">8. SPI</h3>

[Java SPI 机制详解](https://javaguide.cn/java/basis/spi.html)

详见：com.lyq.extension.ExtensionLoader

![](https://cdn.nlark.com/yuque/0/2025/png/330006/1741689323016-2819e5f7-4a9b-4666-8602-467346b191a3.png)