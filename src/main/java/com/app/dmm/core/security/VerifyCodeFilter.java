package com.app.dmm.core.security;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.app.dmm.core.ApiResult;
import com.app.dmm.core.utils.MD5Utils;
import com.app.dmm.core.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @createTime 2020/7/20
 */
@Component
public class VerifyCodeFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    private String defaultFilterProcessUrl = "/system/login";
    private String method = "POST";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (method.equalsIgnoreCase(request.getMethod()) && defaultFilterProcessUrl.equals(request.getServletPath())) {
            // 登录请求校验验证码，非登录请求不用校验
            //HttpSession session = request.getSession();
            String requestCaptcha = request.getParameter("captcha");
            //验证码的信息存放在seesion种，具体看EasyCaptcha官方解释
            //String genCaptcha = (String) request.getSession().getAttribute("captcha");
            //String genCaptcha = (String) request.getSession().getAttribute("captcha");
            String checkKey = request.getParameter("checkKey");
            String lowerCaseCaptcha = requestCaptcha.toLowerCase();
            String realKey = MD5Utils.MD5Encode(lowerCaseCaptcha+checkKey, "utf-8");
            Object checkCode = redisUtil.get(realKey);
            System.out.println("值》》》》》》"+checkCode.toString());

            response.setContentType("application/json;charset=UTF-8");
            if (StrUtil.isEmpty(requestCaptcha)){
                //删除缓存里的验证码信息
                //session.removeAttribute("captcha");
                response.getWriter().write(JSON.toJSONString(ApiResult.error("验证码不能为空!")));
                return;
            }
            if (checkCode ==null){
                response.getWriter().write(JSON.toJSONString(ApiResult.error("验证码已失效!")));
                return;
            }
            if (!StrUtil.equalsIgnoreCase((String)checkCode,requestCaptcha)){
                //session.removeAttribute("captcha");
                response.getWriter().write(JSON.toJSONString(ApiResult.error("验证码错误!")));
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
