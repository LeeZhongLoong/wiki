package com.lzl.wiki.controller;

import com.lzl.wiki.domain.Demo;
import com.lzl.wiki.service.impl.DemoServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
@RequestMapping("/demo")
public class DemoController {
//    引入service
    @Resource
    private DemoServiceImpl demoService;

    @GetMapping("/list")
    public List<Demo> list(){
        return demoService.list();
    }
}
