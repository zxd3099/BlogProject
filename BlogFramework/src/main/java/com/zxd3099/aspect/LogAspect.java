package com.zxd3099.aspect;

import com.alibaba.fastjson2.JSON;
import com.zxd3099.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zxd3099
 * @create 2022-11-21-22:20
 * @description AOP实现日志记录，通过日志记录接口调用信息，便于后期调试排查
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(com.zxd3099.annotation.SystemLog)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable{
        Object result = null;
        try {
            processBefore(joinPoint);
            result = joinPoint.proceed();
            processAfter(result);
        } finally {
            // 结束后换行
            log.info("======= End =======" + System.lineSeparator());
        }
        return result;
    }

    private void processBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强的方法上的注解对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SystemLog annotation = signature.getMethod().getAnnotation(SystemLog.class);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());
        // 打印描述信息
        log.info("Description    : {}", annotation.Description());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
                ((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private void processAfter(Object result) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(result));
    }
}
