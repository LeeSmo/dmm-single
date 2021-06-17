package com.app.dmm.aspect;

import com.alibaba.fastjson.JSON;
import com.app.dmm.annotation.Log;
import com.app.dmm.modules.sys.entity.SysRunLog;
import com.app.dmm.modules.sys.service.SysRunLogService;
import com.app.dmm.modules.user.entity.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SysRunLogService sysRunLogService;

    //@Pointcut("(execution(* com.app..*ServiceImpl.*(..))) or (@annotation(com.app.core.annotation.Log))")
    @Pointcut("@annotation(com.app.dmm.annotation.Log)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            logger.info("@Log operation : " + logAnnotation.value());
        }
        logger.info("CLASS_METHOD : " + signature.getDeclaringTypeName() + "." + signature.getName() + "()");
        Object[] args = joinPoint.getArgs();
        Map<String, Object> argsMap = new HashMap<>();
        for (Object arg : args) {
            if ((arg instanceof HttpServletRequest) || (arg instanceof HttpServletResponse)
                    || (arg instanceof MultipartFile)) {
                continue;
            }
            argsMap.put(arg.getClass().getName(), arg);
        }
        logger.info("参数 : " + JSON.toJSONString(argsMap));
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        logger.info("耗时 : " + time + " ms");
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysRunLog sysRunLog = new SysRunLog();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            sysRunLog.setOperation(logAnnotation.value()); // 注解上的描述
        }
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysRunLog.setMethod(className + "." + methodName + "()");// 请求的方法名
        Object[] args = joinPoint.getArgs();// 请求的参数
        Map<String, Object> argsMap = new HashMap<>();
        for (Object arg : args) {
            if ((arg instanceof HttpServletRequest) || (arg instanceof HttpServletResponse)
                    || (arg instanceof MultipartFile)) {
                continue;
            }
            argsMap.put(arg.getClass().getName(), arg);
        }
        String argsJsonStr = JSON.toJSONString(argsMap);
        argsJsonStr = (argsJsonStr.length() > 4999) ? argsJsonStr.substring(0, 4999) : argsJsonStr;
        sysRunLog.setParams(argsJsonStr);

        /*SysUser user = SpringContextUtil.getUser();
        if (null == user) {
            sysRunLog.setUserId("-1");
            sysRunLog.setUsername("获取用户信息为空");
        } else {
            sysRunLog.setUserId(user.getResourceid());
            sysRunLog.setUsername(user.getUsername());
        }
        sysRunLog.setTime((int) time);
        sysRunLog.setCreateTime(new Date());
        sysRunLogService.save(sysRunLog);*/
    }

}

