package com.app.dmm.config;

import com.app.dmm.core.security.*;
import com.app.dmm.core.security.exception.JwtAccessDeniedHandler;
import com.app.dmm.core.security.exception.JwtAuthenticationEntryPoint;
import com.app.dmm.core.security.handler.MyAuthenticationFailureHandler;
import com.app.dmm.core.security.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity  //开启 spring Security
@EnableGlobalMethodSecurity(prePostEnabled = true)  //
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("myJwtUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    public MyAuthenticationSuccessHandler authenticationSuccessHandler(){
        return new MyAuthenticationSuccessHandler();
    }

    @Bean
    public MyAuthenticationFailureHandler authenticationFailureHandler(){
        return new MyAuthenticationFailureHandler();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义登录身份认证组件
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    //这里是放行静态资源的
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/*")
                .antMatchers("/img/*")
                .antMatchers("/jQuery/*")
                .antMatchers("/js/*")
                .antMatchers("/mp3/*")
                .antMatchers("/logging.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启登录配置
        //http.addFilterBefore(new VerifyCodeFilter(), UsernamePasswordAuthenticationFilter.class);
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        //允许跨域访问
        http.cors();

        //放行swagger
        http.authorizeRequests()
                .antMatchers("/v2/api-docs",//swagger api json
                        "/swagger-resources/configuration/ui",//用来获取支持的动作
                        "/swagger-resources",//用来获取api-docs的URI
                        "/swagger-resources/configuration/security",//安全选项
                        "/swagger-ui.html",
                        "/webjars/**").permitAll();
        //关闭csrf
        http.csrf().disable()
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //未登陆时返回 JSON 格式的数据给前端
                //.and()
                //.httpBasic().authenticationEntryPoint(authenticationEntryPointImp)
                .and()
                .authorizeRequests()
                //任何人都能访问这个请求
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/system/**").permitAll()
                // 查看sql监控(druid)
                .antMatchers("/druid/**").permitAll()
                // 验证码
                .antMatchers("/captcha.jpg**").permitAll()
                .antMatchers("/randomImage/**").permitAll()
                .antMatchers("/websocket/logging").permitAll()
                .antMatchers("/createQRcode").permitAll()
                //除上面配置的路径外，所有的请求都需要进行认证后才能访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // （登录页面或是提示用户未登录的页面 不设限访问）
                .loginPage("/index.html")
                //认证通过后的事件处理（在这里返回token）
                //拦截的请求
                .loginProcessingUrl("/system/login")
                // 登录成功
                .successHandler(authenticationSuccessHandler())
                // 登录失败
                .failureHandler(authenticationFailureHandler())
                .permitAll()
                .and()
                .addFilter(new JwtLoginAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                //设置登录注销url，这个无需我们开发，springsecurity已帮我们做好
                .logout().logoutUrl("/user/logout").permitAll()
                .and()
                //配置 记住我 功能，
                .rememberMe().rememberMeParameter("rememberme");
//                 防止iframe 造成跨域
//                .and()
//                .headers()
//                .frameOptions()
//                .disable();
        http.httpBasic().disable();
        //.and().httpBasic(); //开启HTTP Basic后 ，可用postman通过携带用户名+密码直接请求接口
        // 禁用缓存
        //http.headers().cacheControl();
        http.headers().frameOptions().disable();
        // 开启登录认证流程过滤器
        http.addFilterBefore(new JwtLoginAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // 访问控制时登录状态检查过滤器
        http.addFilterBefore(new JwtAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // 无权访问 JSON 格式的数据
        //http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        http.exceptionHandling().accessDeniedHandler(new JwtAccessDeniedHandler());      //添加无权限时的处理
        //没有认证时，在这里处理结果，不要重定向
        //http.exceptionHandling().authenticationEntryPoint(authenticationEntryPointImp);
        http.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());
        // 退出登录处理器
        // http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

}
