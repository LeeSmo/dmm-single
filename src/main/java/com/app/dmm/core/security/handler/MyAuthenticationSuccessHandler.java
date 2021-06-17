package com.app.dmm.core.security.handler;

import com.alibaba.fastjson.JSON;
import com.app.dmm.core.ApiResult;
import com.app.dmm.core.security.userdetails.JwtDetailsUser;
import com.app.dmm.core.utils.JwtTokenUtils;

import com.app.dmm.core.utils.MD5Util;
import com.app.dmm.core.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 登录成功 处理器
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    //@Value("${jwt.tokenHeader}")
    private String tokenHeader;
    //@Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String requestCaptcha = httpServletRequest.getParameter("captcha");
        String checkKey = httpServletRequest.getParameter("checkKey");
        String lowerCaseCaptcha = requestCaptcha.toLowerCase();
        String realKey = MD5Util.MD5Encode(lowerCaseCaptcha+checkKey, "utf-8");
        Object checkCode = redisUtil.get(realKey);
        System.out.println("值wwqwqw》》》》》》"+checkCode.toString());
         JwtDetailsUser userDetails = (JwtDetailsUser)authentication.getPrincipal();//拿到登录用户信息
         String jwtToken = JwtTokenUtils.createToken(userDetails.getUsername(),userDetails.getRoleIds(),true);//生成token
        //HttpSession session = httpServletRequest.getSession();
        //删除缓存里的验证码信息
        //session.removeAttribute("captcha");
        ApiResult result = ApiResult.OK("登录成功",jwtToken);
        //修改编码格式
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json");
        //输出结果
        httpServletResponse.getWriter().write(JSON.toJSONString(result));

    }
}
