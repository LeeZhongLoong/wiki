//package com.lzl.wiki.interceptor;
//
///**
// * <h3>wiki</h3>
// * <p>日志拦截器</p>
// *
// * @author : 黎钟龙
// * QQ:1661548820
// * Mail：1661548820@qq.com
// * @date : 2022-01-10 14:26
// **/
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 拦截器：spring  框架特有的，常用于登录校验，请求打印
// */
//@Component
//public class LogInterceptor  implements HandlerInterceptor {
//    private static final Logger LOG= LoggerFactory.getLogger(LogInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////        答应请求信息
//        LOG.info("----------LogInterceptor开始----------");
//        LOG.info("请求地址:{}{}",request.getRequestURL().toString(), request.getMethod());
//        LOG.info("远程地址{}", request.getRemoteAddr());
//        long startTime = System.currentTimeMillis();
//        request.setAttribute("requestStartTime",startTime);
//        return true;
//    }
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
//        long startTime = (long) request.getAttribute("requestStartTime");
//        LOG.info("----------LogInterceptor结束 耗时 :{}ms----------",System.currentTimeMillis()-startTime);
//    }
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
//    }
//}
