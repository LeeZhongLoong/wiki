package com.lzl.wiki.config;

import com.lzl.wiki.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * <h3>wiki</h3>
 * <p>配置类</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-10 14:55
 **/

/**
 * 注册拦截器
 */
@Configuration
public class SpringMvcConfig  implements WebMvcConfigurer {

    @Resource
    LoginInterceptor loginInterceptor;
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
//                拦截所有请求,excludePathPatterns(xxx)不拦截请求
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/test/**",
                        "/redis/**",
                        "/user/login",
                        "/category/all",
                        "/ebook/list",
                        "/doc/all/**",
                        "/doc/vote/**",
                        "/doc/find-content/**",
                        "/ebook-snapshot/**"
                        );

    }
}
