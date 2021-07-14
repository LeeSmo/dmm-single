package com.app.dmm.core.security;


import com.app.dmm.core.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 *   验证 登录成功之后走此类进行鉴权操作
 *   token的校验
 *   该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 *   从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 *   如果校验通过，就认为这是一个取得授权的合法请求
 *   @author lee
 *   @date 2020-11-05
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        //判断请求体的头中是否包含Authorization
        String authorization = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (authorization == null || !authorization.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken token;
        try {
            //解析jwt生成的token，获取权限
            token = getAuthentication(authorization);
        } catch (Exception e) {
            //返回json形式的错误信息
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            String reason = "统一处理，原因：" + e.getMessage();
            response.getWriter().write(new ObjectMapper().writeValueAsString(reason));
            response.getWriter().flush();
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        //获取后，将Authentication写入SecurityContextHolder中供后序使用
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }
    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String authorization) {
        //解析token

        String token = authorization.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String username = JwtTokenUtils.getUsername(token.trim());
        String role = JwtTokenUtils.getUserRole(token.trim());
        System.err.println("token:" + token);
        System.err.println("username:" + username);
        System.err.println("role:" + role);
        if (username != null){
            return new UsernamePasswordAuthenticationToken(username, null,
                   Collections.singleton(new SimpleGrantedAuthority(role))

            );
        }
        return null;
    }
}
