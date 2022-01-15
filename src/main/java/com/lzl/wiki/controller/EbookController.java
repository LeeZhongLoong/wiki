package com.lzl.wiki.controller;

import com.lzl.wiki.req.EbookQueryReq;
import com.lzl.wiki.req.EbookSaveReq;
import com.lzl.wiki.resp.CommonResp;
import com.lzl.wiki.resp.EbookResp;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.service.impl.EbookServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
//    开启参数校验 @Valid
    public CommonResp list(@Valid EbookQueryReq req){
//        创建一个统一返回值的类型
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();
//        获取数据库汇总的Ebook的记录
        PageResp<EbookResp> list=ebookService.list(req);
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
    public CommonResp save(@RequestBody EbookSaveReq req){
        CommonResp resp=new CommonResp();
        ebookService.save(req);
        return resp;
    }

    /**
     * 删除的方法
     * @param id 根据id删除
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp del(@PathVariable Long id){
        CommonResp resp=new CommonResp();
        ebookService.del(id);
        return resp;
    }
}
