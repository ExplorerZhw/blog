package com.zhw.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurationSupport {
    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "user";

    @Bean
    public InterceptorConfig getSecurityInterceptor() {
        return new InterceptorConfig();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        // 排除配置
        addInterceptor.excludePathPatterns("/index**");
        addInterceptor.excludePathPatterns("/static/**");
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.excludePathPatterns("*.go");
        // 拦截配置
        addInterceptor.addPathPatterns("/manage/**");
        addInterceptor.addPathPatterns("*.do");
    }

//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
//    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/")
//                .setViewName("forward:/index.html");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        super.addViewControllers(registry);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}