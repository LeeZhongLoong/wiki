package com.lzl.wiki.service;

import com.lzl.wiki.resp.StatisticResp;

import java.util.List;

/**
 * <h3>wiki</h3>
 * <p>电子书快照Service</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-15 17:44
 **/
public interface EbookSnapshotService {
//    定时任务电子快照
    public void genSnapshot();

//    获取两天的点赞数和阅读数
    public List<StatisticResp> getStatistic();
//    获取30天的记录
    public List<StatisticResp> get30Statistic();


}
