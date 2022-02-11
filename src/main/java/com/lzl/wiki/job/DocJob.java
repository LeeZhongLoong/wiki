package com.lzl.wiki.job;

import com.lzl.wiki.service.impl.DocServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * 自定义时间定时器
     * 定时器使用同一个线程
     * 只有等待上一次执行完成，下一次才会在下一个时间点执行，错过就错过
     */
    @Scheduled(cron = "5/30 * * * * ?")
    public void cron(){
        docService.updateEbookInfo();
    }

}
