package com.lzl.wiki.controller;

import com.lzl.wiki.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.BindException;

/**
 * <h3>wiki</h3>
 * <p>统一异常处理</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-15 17:36
 **/
//统一异常注解
@ControllerAdvice
public class ControllerExceptionHandler {
    //    日志放入类
        private static final Logger LOG= LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 异常校验统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp validExceptionHandler(BindException e){
        CommonResp commonResp=new CommonResp();
        LOG.warn("参数校验失败{}",e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//        设置错误
        commonResp.setSuccess(false);
//        把异常信息放入到返回统一返回对象中的message中
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }


}
