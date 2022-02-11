//package com.lzl.wiki.job;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * <h3>wiki</h3>
// * <p>测试使用定时器</p>
// *
// * @author : 黎钟龙
// * QQ:1661548820
// * Mail：1661548820@qq.com
// * @date : 2022-02-11 16:29
// **/
//@Component
//public class TestJob {
//    //    日志放入类
//    private static final Logger LOG= LoggerFactory.getLogger(TestJob.class);
//
//    /**
//     * 固定时间，fixedRate单位毫秒
//     */
//    @Scheduled(fixedRate = 5000)
//    public void simple() throws InterruptedException {
//        SimpleDateFormat format=new SimpleDateFormat("mm:ss");
//        String dateString=format.format(new Date());
////        Thread.sleep(2000);
//        LOG.info("每隔五秒钟执行一次:{}",dateString);
//    }
//
//    /**
//     * 自定义时间定时器
//     * 定时器使用同一个线程
//     * 只有等待上一次执行完成，下一次才会在下一个时间点执行，错过就错过
//     */
//    @Scheduled(cron = "*/2 * * * * ?")
//    public void cron() throws InterruptedException {
//        SimpleDateFormat format=new SimpleDateFormat("mm:ss:SSS");
//        String dateString=format.format(new Date());
////        Thread.sleep(1500);
//        LOG.info("每隔1.5秒执行一次:{}",dateString);
//    }
//
//}
