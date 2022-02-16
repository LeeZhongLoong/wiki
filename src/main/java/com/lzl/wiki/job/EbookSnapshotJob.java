package com.lzl.wiki.job;

import com.lzl.wiki.service.impl.EbookSnapshotServiceImpl;
import com.lzl.wiki.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <h3>wiki</h3>
 * <p>Ebook电子书快照</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-15 17:33
 **/
@Component
public class EbookSnapshotJob {
//    日志放入类
    private static final Logger LOG= LoggerFactory.getLogger(EbookSnapshotJob.class);

    @Resource
    private EbookSnapshotServiceImpl ebookSnapshotService;

    @Resource
    private SnowFlake snowFlake;

    /**
     *
     * 自定义cron
     *只有等上一次执行完成，下一次才会在下一个时间点执行，错过就错过
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void doSnapshot(){
//        增加日志流水号
        MDC.put("LOG_ID",String.valueOf(snowFlake.nextId()));
        LOG.info("生成今日电子书快照开始");
        long start =System.currentTimeMillis();
        ebookSnapshotService.genSnapshot();
        LOG.info("生成今日电子书快照结束，耗时:{}毫秒",System.currentTimeMillis()-start);
    }

}
