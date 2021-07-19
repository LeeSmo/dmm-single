package com.app.dmm.aspect;

import com.app.dmm.annotation.IgnoreToken;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * 放行token切面
 */
@Aspect
@Component
public class IgnoreTokenAspect {

    private  static Logger logger = LogManager.getLogger(IgnoreTokenAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Pointcut("execution(* com.app.dmm.modules.*.*Controller.*(..))")
    public void methodPointCut() {
    }

    /**
     * 某个方法执行前进行请求认证 注入IgnoreToken注解 （先）
     */
    @Before("requestMapping() && methodPointCut()&&@annotation(ignoreToken)")
    public void doBefore(JoinPoint joinPoint, IgnoreToken ignoreToken) throws Exception {

        logger.info("方法放行token ...");

        Class type = joinPoint.getSignature().getDeclaringType();

        Annotation[] annotations = type.getAnnotationsByType(IgnoreToken.class);

        if (annotations != null && annotations.length > 0) {
            logger.info("直接类调用 ...");
            return;
        }

        //获取当前http请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

       // String token = request.getHeader(AppConst.AUTH_TOKEN);

      //  BizResult<String> bizResult = authTokenService.powerCheck(token);

       // System.out.println(SerializeUtil.Serialize(bizResult));

        /*if (bizResult.getIsOK() == true) {
            PowerLogger.info("方法认证通过");
        } else {
            throw new Exception(bizResult.getMessage());
        }*/
    }
}
