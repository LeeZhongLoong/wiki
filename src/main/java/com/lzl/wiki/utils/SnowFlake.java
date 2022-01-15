package com.lzl.wiki.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * <h3>wiki</h3>
 * <p>雪花算法</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-15 12:42
 *
 * 时间错秒速
 * 根据1970-01-01 08:00:00 来计算
 **/

@Component
public class SnowFlake {

//  更改起止时间 2021年1月1日8点
    private final static long START_STMP= 1609459200000L;
    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT=12; //序列号的位数
    private final static long MACHINE_BIT=5; //机器标识占用的位数
    private final static long DATACENTER_BIT=5; //数据中心占用的位数

    /**
     * 每一部分最大的值
     * 位运算
     */
    private final static long MAX_DATACENTER_NUM=-1L ^ (-1L<< DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM=-1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE=-1L ^ (-1L<< SEQUENCE_BIT);

    /**
    /**
     * 每一部分向左的移动
     */
    private final static long MACHINE_LEFT=SEQUENCE_BIT;
    private final static long DATACENTER_LEFT=DATACENTER_BIT+MACHINE_BIT;
    private final static long TIMESTMP_LEFT=DATACENTER_LEFT+DATACENTER_BIT;

    private long datacenterId=1; //数据中心
    private long machineId=1; //机器标识
    private long sequence=0L; //序列号
    private long lastStep=-1L; //上一次时间戳

//    构造方法
    public SnowFlake(){

    }

    public SnowFlake(long datacenterId,long machineId){
        if (datacenterId>MAX_DATACENTER_NUM || datacenterId<0){
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or" +
                    "less than 0");
        }
        if (machineId>MAX_MACHINE_NUM || machineId<0){
            throw new IllegalArgumentException("machineId can't be greater than MAX_DATACENTER_NUM " +
                    "or less than 0");
        }
        this.datacenterId=datacenterId;
        this.machineId=machineId;
    }

    private long getNewStmp(){
        return System.currentTimeMillis();
    }
    public synchronized long nextId(){
//        获得现在的时间戳
        long currStmp=getNewStmp();
        if (currStmp<lastStep){
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }
        if (currStmp==lastStep){
//            相同毫秒内，序列号自增
            sequence=(sequence+1) & MAX_SEQUENCE;
//            同一毫秒内，序列号置0
           if (sequence==0L){
               currStmp=getNextMill();
           }
        }else {
//            不同毫秒内，序列号为0
            sequence=0L;
        }
        lastStep=currStmp;
//        将数据拼接
        return (currStmp-START_STMP)<<TIMESTMP_LEFT //时间戳部分
                | datacenterId <<DATACENTER_LEFT    //数据中心部分
                | machineId << MACHINE_LEFT         //机器标识部分
                | sequence;
    }
    private long getNextMill(){
        long mill=getNewStmp();
        while (mill<=lastStep){
            mill=getNewStmp();
        }
        return mill;
    }
    public static void main(String[] args) throws ParseException {
////       时间戳
//        System.out.println(System.currentTimeMillis());
//        System.out.println(new Date().getTime());
//        String defaultDateTime ="2021-01-01 08:00:00";
////        转换格式
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        System.out.println(sdf.parse(defaultDateTime).getTime());
        //使用雪花算法案例
        SnowFlake snowFlake=new SnowFlake(1,1);
//        生成当前时间
        Long start=System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            System.out.println(snowFlake.nextId());
            System.out.println(System.currentTimeMillis()-start);
        }

    }
}
