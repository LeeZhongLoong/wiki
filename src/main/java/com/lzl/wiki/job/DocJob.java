package com.lzl.wiki.job;

import com.lzl.wiki.service.impl.DocServiceImpl;
import com.lzl.wiki.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <h3>wiki</h3>
 * <p>测试使用定时器</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-11 16:29
 **/
@Component
public class DocJob {
    //    日志放入类
    private static final Logger LOG= LoggerFactory.getLogger(DocJob.class);
//    引入docMapperCust
    @Resource
    private DocServiceImpl docService;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 自定义时间定时器
     * 定时器使用同一个线程
     * 只有等待上一次执行完成，下一次才会在下一个时间点执行，错过就错过
     */
    @Scheduled(cron = "5/30 * * * * ?")
    public void cron(){
//        增加业务流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        long startTime = System.currentTimeMillis();
        LOG.info("更新电子书下的文档数据开始");
        docService.updateEbookInfo();
        LOG.info("更新电子书下的文档数据结束，耗时：{}毫秒,",System.currentTimeMillis()-startTime);
    }

}
