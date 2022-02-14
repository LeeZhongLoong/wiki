package com.lzl.wiki.rocketmq;

import com.lzl.wiki.websocket.WebSocketServer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <h3>wiki</h3>
 * <p>RocketMQ服务端控制</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-13 18:12
 **/
@Service
@RocketMQMessageListener(consumerGroup = "default",topic = "VOTE_TOPIC")
public class VoteTopicConsumer implements RocketMQListener<MessageExt> {
//    日志放入类
    private static final Logger LOG= LoggerFactory.getLogger(VoteTopicConsumer.class);
//    注入webservice

    @Resource
    public WebSocketServer webSocketServer;
    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body=messageExt.getBody();
        LOG.info("ROCKETMQ收到消息:{}",new String(body));
        //        增加流水号
        webSocketServer.sendInfo(new String(body ));
    }
}
