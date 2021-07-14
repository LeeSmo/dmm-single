package com.app.dmm.core.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.dmm.core.security.userdetails.JwtDetailsUser;
import com.app.dmm.core.utils.JwtTokenUtils;
import com.app.dmm.modules.sys.dto.request.SysLoginVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *  登录认证检查过滤器
 *  验证用户名密码正确后，生成一个token，并将token返回给客户端
 *  该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法 ,
 *  attemptAuthentication：接收并解析用户凭证。
 *  successfulAuthentication：用户成功登录后，这个方法会被调用，我们在这个方法里生成token并返回。
 *  @author lee
 *  @date 2020-11-05
 */
public class JwtLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private MyJwtUserDetailsServiceImpl myJwtUserDetailsService;
    private AuthenticationManager authenticationManager;

    public JwtLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //super.setFilterProcessesUrl("/auth/login");   00-0C-29-D0-3A-18   52-54-00-23-54-28
    }
    // 接收并解析用户凭证,登录入口
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        //判断请求是否为POST,禁用GET请求提交数据
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "只支持POST请求方式");
        }
        // 从输入流中获取到登录的信息
        try {
           SysLoginVo sysLoginVo = new ObjectMapper().readValue(request.getInputStream(), SysLoginVo.class);
            //String password = "$2a$10$cAhEt3x1Xato25YOf3uHB.jhnliJ4625SOc5WttfPOTsEFBKkRdue";
            //if(!BPwdEncoderUtil.matches(password,)){}
            //    CustomDetailsUser user = (CustomDetailsUser) myJwtUserDetailsService.loadUserByUsername(loginId);
            //    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(sysLoginVo.getUsername(), sysLoginVo.getPassword())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        JwtDetailsUser jwtDetailsUser = (JwtDetailsUser) authResult.getPrincipal();
        System.err.println("jwtDetailsUser:{}" + jwtDetailsUser.toString());

        String role = "";
        Collection<? extends GrantedAuthority> authorities = jwtDetailsUser.getAuthorities();
        for (GrantedAuthority authority : authorities){
            role = authority.getAuthority();
        }
        String token = JwtTokenUtils.createToken(jwtDetailsUser.getUsername(), role,true);
        //String token = JwtTokenUtils.generateToken(jwtCustomUser.getUsername(), role);
        //String token = JwtTokenUtils.createToken(jwtUser.getUsername(), false);
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String tokenStr = JwtTokenUtils.TOKEN_PREFIX + token;
        response.setHeader("token",tokenStr);
    }




}
