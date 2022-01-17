package com.lzl.wiki.service;

import com.lzl.wiki.req.CategoryQueryReq;
import com.lzl.wiki.req.CategorySaveReq;
import com.lzl.wiki.resp.CategoryQueryResp;
import com.lzl.wiki.resp.PageResp;

/**
 * <h3>wiki</h3>
 * <p>测试类的Service</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 **/
public interface CategoryService {
//    查询的方法
    public PageResp<CategoryQueryResp> list(CategoryQueryReq req);
//    保存的方法
    public void save(CategorySaveReq req);
//    删除的方法
    public void del(Long id);
}
