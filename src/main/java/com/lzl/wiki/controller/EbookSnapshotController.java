package com.lzl.wiki.controller;

import com.lzl.wiki.resp.CommonResp;
import com.lzl.wiki.resp.StatisticResp;
import com.lzl.wiki.service.impl.EbookSnapshotServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <h3>wiki</h3>
 * <p>电子书快照</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-16 19:46
 **/
@RestController
@RequestMapping("/ebook-snapshot")
public class EbookSnapshotController {
    @Resource
    private EbookSnapshotServiceImpl ebookSnapshotService;

    @GetMapping("/get-statistic")
    public CommonResp getStatistic(){
        List<StatisticResp> statisticResp= ebookSnapshotService.getStatistic();
        CommonResp<StatisticResp> commonResp=new CommonResp<>();
        return commonResp;
    }


}
