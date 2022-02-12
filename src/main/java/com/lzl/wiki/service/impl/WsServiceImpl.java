package com.lzl.wiki.service.impl;

import com.lzl.wiki.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <h3>wiki</h3>
 * <p></p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-12 19:21
 **/
@Service
public class WsServiceImpl {

    @Resource
    public WebSocketServer webSocketServer;
//    要使Async这个方法不能和调用的地方在同一个类
    @Async
    public void sendInfo(String contextInfo,String logId) {
        MDC.put("LOG_ID",logId);
        webSocketServer.sendInfo(contextInfo);
    }
}
