package com.lzl.wiki.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <h3>wiki</h3>
 * <p>日志的过滤器</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-10 13:56
 **/
@Component
public class LogFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(LogFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        打印请求信息
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        LOG.info("----------LogFilter开始的地方----------");
        LOG.info("请求地址:{}{}",request.getRequestURL().toString(), request.getMethod());
        LOG.info("远程地址{}", request.getRemoteAddr());
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        LOG.info("----------LogFilter结束耗时:{}ms----------", System.currentTimeMillis() - startTime);
    }

    @Override
    public void destroy() {

    }
}
