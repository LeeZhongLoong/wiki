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
//    增加阅读数
    public void increaseViewCount(@Param("id") Long id);
//    增加点赞数
    public void increaseVoteCount(@Param("id") Long id);
//    统计文档树和点赞数
    public void updateEbookInfo();

}
