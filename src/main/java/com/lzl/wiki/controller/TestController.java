package com.lzl.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    /**
     * 常用request请求有：GET,POST,PUT,DELETE
     *常见错误类型405请求不支持，404没有这样一个接口
     *
     * @return
     */
    @GetMapping("/hello")
    public String hello(){
        return "HelloWorld";
    }
}
