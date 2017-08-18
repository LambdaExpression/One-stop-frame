package org.tcat.frame.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tcat.frame.interceptor.TestInterceptor;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器链添加顺序决定拦截顺序
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new TestInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}