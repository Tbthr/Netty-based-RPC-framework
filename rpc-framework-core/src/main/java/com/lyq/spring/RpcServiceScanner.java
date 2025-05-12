package com.lyq.spring;

import com.lyq.annotation.RpcService;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 自定义的包扫描器
 */
public class RpcServiceScanner extends ClassPathBeanDefinitionScanner {

    public RpcServiceScanner(BeanDefinitionRegistry registry) {
        // new AnnotationConfigApplicationContext(NettyServerMain.class);
        // 默认会生成 ClassPathBeanDefinitionScanner，但不会调用 scan(...)
        // 所以在这里需要包含默认的 filter 来支持 @Component
        super(registry, true);
        super.addIncludeFilter(new AnnotationTypeFilter(RpcService.class));
    }

    @Override
    public int scan(String... basePackages) {
        return super.scan(basePackages);
    }
}