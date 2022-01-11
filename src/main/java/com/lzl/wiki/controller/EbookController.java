package com.lzl.wiki.controller;

import com.lzl.wiki.req.EbookReq;
import com.lzl.wiki.resp.CommonResp;
import com.lzl.wiki.resp.EbookResp;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.service.impl.EbookServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
@RequestMapping("/ebook")
public class EbookController {
//    引入service
    @Resource
    private EbookServiceImpl ebookService;

    /**
     * 根据名字模糊查询
     * @param req
     * @return
     */
    @GetMapping("/list")
    public CommonResp list(EbookReq req){
//        创建一个统一返回值的类型
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
//        获取数据库汇总的Ebook的记录
        PageResp<EbookResp> list=ebookService.list(req);
//        放入到泛型类中
        resp.setContent(list);
        return resp;
    }
}
