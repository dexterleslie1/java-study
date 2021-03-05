此demo演示spring-cloud zuul route hystrix、feign hystrix、resttemplate hystrix用法

springcloud feign fallback，通过feign、HystrixCommand、FallbackProvider
https://www.cnblogs.com/cearnach/p/9341593.html

springcloud zuul FallbackProvider基本使用，能够根据serviceId调用不同的fallback，不能实现针对route调用不同的fallback
fallback的FallbackProvider使用方法只作用于springcloud zuull，不能作用于fallback的feign、HystrixCommand使用方法
https://juejin.cn/post/6844903862470443015
https://thepracticaldeveloper.com/hystrix-fallback-with-zuul-and-spring-boot/

springcloud hystrix配置
https://blog.csdn.net/hry2015/article/details/78554846

HystrixCommand配置
https://github.com/Netflix/Hystrix/wiki/Configuration#intro

springcloud zuul hystrix超时使用FallbackProvider统一处理
feign hystrix超时使用@ControllerAdvice、@ExceptionHandler统一处理

hystrix资源隔离原理图
https://www.cnblogs.com/-beyond/p/12856421.html

RestTemplate hystrix整合
https://blog.51cto.com/13538361/2426289

TODO: 写demo演示关闭ribbon、feign、resttemplate retry机制

