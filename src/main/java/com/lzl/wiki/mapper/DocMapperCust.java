package com.lzl.wiki.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * <h3>wiki</h3>
 * <p>测试mapper</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-08 13:08
 **/
public interface DocMapperCust {
    public void increaseViewCount(@Param("id") Long id);
}
