package com.lzl.wiki.controller;

import com.lzl.wiki.req.CategoryQueryReq;
import com.lzl.wiki.req.CategorySaveReq;
import com.lzl.wiki.resp.CommonResp;
import com.lzl.wiki.resp.CategoryQueryResp;
import com.lzl.wiki.resp.PageResp;
import com.lzl.wiki.service.impl.CategoryServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
@RequestMapping("/category")
public class CategoryController {
//    引入service
    @Resource
    private CategoryServiceImpl categoryService;

    /**
     * 根据名字模糊查询
     * @param req
     * @return
     */
    @GetMapping("/list")
//    开启参数校验 @Valid
    public CommonResp list(@Valid CategoryQueryReq req){
//        创建一个统一返回值的类型
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
//        获取数据库汇总的Category的记录
        PageResp<CategoryQueryResp> list=categoryService.list(req);
//        放入到泛型类中
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/all")
    public CommonResp all(){
//        创建一个统一返回值的类型
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>();
//        获取数据库汇总的Category的记录
        List<CategoryQueryResp> list=categoryService.all();
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
    public CommonResp save(@Valid @RequestBody CategorySaveReq req){
        CommonResp resp=new CommonResp();
        categoryService.save(req);
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
        categoryService.del(id);
        return resp;
    }
}
