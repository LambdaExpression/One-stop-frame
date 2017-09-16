package org.tcat.frame.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tcat.frame.interceptor.ILoginInterceptor;
import org.tcat.frame.util.PropertiesUtil;

import java.util.Properties;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

    private static Logger logger = LoggerFactory.getLogger(InterceptorConfiguration.class);
    private static Properties properties = PropertiesUtil.loadPropertiesFromResourceFile("config.properties");

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器链添加顺序决定拦截顺序
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new ILoginInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}