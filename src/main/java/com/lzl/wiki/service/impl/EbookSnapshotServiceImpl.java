package com.lzl.wiki.service.impl;

import com.lzl.wiki.mapper.EbookSnapshotMapperCust;
import com.lzl.wiki.resp.StatisticResp;
import com.lzl.wiki.service.EbookSnapshotService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <h3>wiki</h3>
 * <p></p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-15 17:44
 **/
@Service
public class EbookSnapshotServiceImpl implements EbookSnapshotService {

    @Resource
    private EbookSnapshotMapperCust ebookSnapshotMapperCust;
    @Override
    public void genSnapshot() {
        ebookSnapshotMapperCust.genSnapshot();
    }

    @Override
    public List<StatisticResp> getStatistic() {
        return ebookSnapshotMapperCust.getStatistic();
    }
}
