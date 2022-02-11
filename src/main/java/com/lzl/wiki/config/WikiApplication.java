package com.lzl.wiki.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.lzl.wiki.mapper")
@ComponentScan("com.lzl")
@SpringBootApplication
@EnableScheduling
public class WikiApplication {

//    添加日志
    private static final Logger LOG= LoggerFactory.getLogger(WikiApplication.class);

    public static void main(String[] args) {
        SpringApplication app=new SpringApplication(WikiApplication.class);
//        SpringApplication.run(WikiApplication.class, args);
        Environment env=app.run(args).getEnvironment();
        LOG.info("启动成功!!");
        LOG.info("地址: \thttp://127.0.0.1:{}",env.getProperty("server.port"));

    }

}
