package com.lzl.wiki.service;

import com.lzl.wiki.req.DocQueryReq;
import com.lzl.wiki.req.DocSaveReq;
import com.lzl.wiki.resp.DocQueryResp;
import com.lzl.wiki.resp.PageResp;

import java.util.List;

/**
 * <h3>wiki</h3>
 * <p>测试类的Service</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 **/
public interface DocService {
//    查询的方法
    public PageResp<DocQueryResp> list(DocQueryReq req);
//    保存的方法
    public void save(DocSaveReq req);
//    删除的方法
    public void del(Long id);
    public void del(List<String> ids);
//    只查一页
    public List<DocQueryResp> all(Long ebookId);
//    查询content中的内容
    public String findContent(Long id);
//    点赞数增加
    public void vote(Long id);
//    统计文档
    public void updateEbookInfo();
}
