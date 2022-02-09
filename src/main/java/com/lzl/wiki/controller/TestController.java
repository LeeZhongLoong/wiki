package com.lzl.wiki.controller;

import com.lzl.wiki.domain.Test;
import com.lzl.wiki.service.impl.TestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <h3>wiki</h3>
 * <p>HelloWorld</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-08 08:37
 **/
//返回的是一个字符串
//@Controller
//    返回json格式字符串
@RestController
public class TestController {
//    加入redis
    @Resource
    private RedisTemplate<Long,String> redisTemplate;
//    日志
    //    日志放入类
        private static final Logger LOG= LoggerFactory.getLogger(TestController.class);
//    使用自定义变量,如果自定义配置项没有开启就默认读出Test
    @Value("${test.hello:Test}")
    private String testHello;

//    引入service
    @Resource
    private TestServiceImpl testService;
    /**
     * 常用request请求有：GET,POST,PUT,DELETE
     *常见错误类型405请求不支持，404没有这样一个接口
     *
     * @return
     */
    @GetMapping("/hello")
    public String hello(){
        return "HelloWorld"+testHello;
    }
    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "HelloWorld !post"+name;
    }

    @GetMapping("/test/list")
    public List<Test> list(){
        return testService.list();
    }

//    添加key和value
    @RequestMapping("/redis/set/{key}/{value}")
    public String set(@PathVariable Long key,@PathVariable String value){
        redisTemplate.opsForValue().set(key,value,3600*24, TimeUnit.SECONDS);
        LOG.info("key:{},value:{}",key,value);
        return "success";
    }
//  获取key中的value
    @RequestMapping("/redis/get/{key}")
    public Object get(@PathVariable Long key){
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key:{},value:{}",key,object);
        return object;
    }
}
