package com.lzl.wiki.controller;

import com.lzl.wiki.req.DocQueryReq;
import com.lzl.wiki.req.DocSaveReq;
import com.lzl.wiki.resp.CommonResp;
import com.lzl.wiki.resp.DocQueryResp;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.service.impl.DocServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
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
@RequestMapping("/doc")
public class DocController {
//    引入service
    @Resource
    private DocServiceImpl docService;


    /**
     * 根据名字模糊查询
     * @param req
     * @return
     */
    @GetMapping("/list")
//    开启参数校验 @Valid
    public CommonResp list(@Valid DocQueryReq req){
//        创建一个统一返回值的类型
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
//        获取数据库汇总的Doc的记录
        PageResp<DocQueryResp> list=docService.list(req);
//        放入到泛型类中
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable Long ebookId){
//        创建一个统一返回值的类型
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
//        获取数据库汇总的Doc的记录
        List<DocQueryResp> list=docService.all(ebookId);
//        放入到泛型类中
        resp.setContent(list);
        return resp;
    }

//    保存使用post方法

    /**
     * 修改值并保存的方法
     * @param req
     * @return
     */
    @PostMapping("/save")
//    @RequestBody 请求参数是json格式，@ResponseBody是返回参数是json格式
//    @Valid参数校验
    public CommonResp save(@Valid @RequestBody DocSaveReq req){
        CommonResp resp=new CommonResp();
        docService.save(req);
        return resp;
    }

    /**
     * 删除的方法
     * @param idsStr 根据id删除
     * @return
     */
    @DeleteMapping("/delete/{idsStr}")
    public CommonResp del(@PathVariable String idsStr){
        CommonResp resp=new CommonResp();
//        将字符串转化为数组，然后将数组转化为集合
        List<String> list = Arrays.asList(idsStr.split(","));
        docService.del(list);
        return resp;
    }

    @GetMapping("/find-content/{id}")
//    开启参数校验 @Valid
    public CommonResp findContent(@PathVariable Long id){
//        创建一个统一返回值的类型
        CommonResp<String> resp = new CommonResp<>();
//        获取数据库汇总的Doc的记录
        String content=docService.findContent(id);
//        放入到泛型类中
        resp.setContent(content);
        return resp;
    }

    @GetMapping("/vote/{id}")
    public CommonResp vote(@PathVariable Long id){
        CommonResp commonResp=new CommonResp();
        docService.vote(id);
        return commonResp;
    }
}
