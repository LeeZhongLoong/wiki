package com.lzl.wiki.service;

import com.lzl.wiki.req.EbookQueryReq;
import com.lzl.wiki.req.EbookSaveReq;
import com.lzl.wiki.resp.EbookResp;
import com.lzl.wiki.resp.PageResp;

/**
 * <h3>wiki</h3>
 * <p>测试类的Service</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 **/
public interface EbookService {
//    查询的方法
    public PageResp<EbookResp> list(EbookQueryReq req);
//    保存的方法
    public void save(EbookSaveReq req);
}
