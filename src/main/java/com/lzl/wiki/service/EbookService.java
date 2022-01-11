package com.lzl.wiki.service;

import com.lzl.wiki.req.EbookReq;
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
    public PageResp<EbookResp> list(EbookReq req);
}
