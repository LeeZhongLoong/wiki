package com.lzl.wiki.utils;

import java.io.Serializable;

/**
 * <h3>wiki</h3>
 * <p>过滤不同Ip登录Reques文本域</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-02-11 14:17
 **/
public class RequestContext implements Serializable {
    private static ThreadLocal<String> remoteAddr=new ThreadLocal<>();

    public static String getRemoteAddr(){
        return remoteAddr.get();
    }

    public static void setRemoteAddr(String remoteAddr){
        RequestContext.remoteAddr.set(remoteAddr);
    }

}
