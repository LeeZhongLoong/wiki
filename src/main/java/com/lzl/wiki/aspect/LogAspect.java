package com.lzl.wiki.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.lzl.wiki.utils.RequestContext;
import com.lzl.wiki.utils.SnowFlake;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <h3>wiki</h3>
 * <p>LogAop打印接口返回参数</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-10 15:05
 **/

/**
 * 陷入fastjson依赖，和aop依赖
 */
@Aspect
@Component
public class LogAspect {
    private final static Logger LOG= LoggerFactory.getLogger(LogAspect.class);

//    定义一个切点（controller中的所有类所有方法）
    @Pointcut("execution(public * com.lzl.*.controller..*Controller.*(..))")
    public void controllerPointCut(){}
//    引入雪花算法
    @Resource
    private SnowFlake snowFlake;
//前置通知
    @Before("controllerPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{

//        增加日子流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
//        开始打印请求日志
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        Signature signature=joinPoint.getSignature();
        String name=signature.getName();
//        打印请求信息
        LOG.info("----------LogAOP开始----------");
        LOG.info("请求地址:{}{}",request.getRequestURL().toString(), request.getMethod());
        LOG.info("类名方法:{}.{}",signature.getDeclaringTypeName(),name);
        LOG.info("远程地址:{}", request.getRemoteAddr());

        RequestContext.setRemoteAddr(getRemoteIp(request));
//        拿到所有参数
//       打印请求参数
        Object[] args=joinPoint.getArgs();
        Object[] arguments=new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile){
                continue;
            }
            arguments[i]=args[i];
        }
//        排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties={"password","file"};
        PropertyPreFilters filters =new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter=filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        LOG.info("请求参数:{}", JSONObject.toJSONString(arguments,excludeFilter));
    }

//  环绕通知
    @Around("controllerPointCut()")
public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime=System.currentTimeMillis();
        Object result=proceedingJoinPoint.proceed();
//        排除字段，铭感字段太长不显示
        String[] excludeProperties={"password","file"};
        PropertyPreFilters filters =new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter=filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        LOG.info("返回结果:{}", JSONObject.toJSONString(result,excludeFilter));
        LOG.info("----------结果 耗时:{}ms----------",System.currentTimeMillis()-startTime);
        return result;
    }

    /**
     * 使用nginx做反向代理,需要用该方法才能渠道真实的远程IP
     * @param request
     * @return
     */
    public String getRemoteIp(HttpServletRequest request){
        String ip=request.getHeader("x-forwarded-for");
        if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("Proxy-Client-IP");
        }if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("WL-Proxy-Client-IP");
        }if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip=request.getRemoteAddr();
        }
        return ip;
    }
}
