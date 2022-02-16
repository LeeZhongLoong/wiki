package com.lzl.wiki.mapper;

import com.lzl.wiki.resp.StatisticResp;

import java.util.List;

/**
 * <h3>wiki</h3>
 * <p>自定义电子书快照mapper</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-15 17:59
 **/
public interface EbookSnapshotMapperCust {
    public void genSnapshot();

    List<StatisticResp> getStatistic();
}
