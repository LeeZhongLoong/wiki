package com.lzl.wiki.service.impl;

import com.lzl.wiki.service.DocServiceCust;

import javax.annotation.Resource;

/**
 * <h3>wiki</h3>
 * <p></p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-11 12:01
 **/
public class DocServiceCustImpl implements DocServiceCust {
    @Resource
    DocServiceCust docMapperCust;
    @Override
    public void increaseViewCount(Long id) {
        docMapperCust.increaseViewCount(id);
    }
}
