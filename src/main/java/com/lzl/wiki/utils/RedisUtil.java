package com.lzl.wiki.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <h3>wiki</h3>
 * <p>判断redis中是否有ip已经登录已经登录并点赞的key不能再次点赞</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-11 14:41
 **/
@Component
public class RedisUtil {

    //    日志放入类
        private static final Logger LOG= LoggerFactory.getLogger(RedisUtil.class);
//        引入redis
    @Resource
    private RedisTemplate redisTemplate;
    /**
     * true：不存在，放在一个Key
     * false：已存在
     *
     */
    public boolean validateRepeat(String key,long second){
        if (redisTemplate.hasKey(key)){
            LOG.info("key已存在:{}",key);
            return false;
        }else {
            LOG.info("key不存在,放入:{},过期{}秒",key,second);
//            往redis中放值要考虑过期时长。
            redisTemplate.opsForValue().set(key,key,second,TimeUnit.SECONDS);
            return true;
        }
    }
}
