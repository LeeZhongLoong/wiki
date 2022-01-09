package com.lzl.wiki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <h3>wiki</h3>
 * <p>前端和后端解跨域的问题</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-09 19:09
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 允许前端带上凭证Cookie，用于登录校验
                .allowCredentials(true)
                // 设置允许的header属性
                .allowedHeaders(CorsConfiguration.ALL)
                // 设置允许的方法
                .allowedMethods(CorsConfiguration.ALL)
                // 跨域允许时间
                .maxAge(3600);
    }
}
