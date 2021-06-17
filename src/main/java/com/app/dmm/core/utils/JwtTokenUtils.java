package com.app.dmm.core.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
     *  JWT 由3部分组成: header(Map集合),playload(负载,也可以把它看做请求体body,也是一个map集合),signature(签名,有header和playload加密后再跟secrect加密生成)
     *  header:有2个值,一个是类型,一个是算法,类型就是JWT,不会变,算法有2种选择,HMAC256和RS256,基本选择HMAC256
     *  playload:类似于post请求的请求体,是一个map集合,可以存很多很多值,如存用户的信息
     *  signature:由header(Base64加密后)和playload(Base64加密后)再加上secrect(秘钥生成)
     *  Base64加密是可逆的,所以存在header和playload的数据不能是敏感数据
     *
     *  playload有一些值定义:
     *
     *

     iss: jwt签发者

     sub: jwt所面向的用户

     aud: 接收jwt的一方

     exp: jwt的过期时间，这个过期时间必须要大于签发时间

     nbf: 定义在什么时间之前，该jwt都是不可用的.

     iat: jwt的签发时间

     jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。

     */

public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    //密钥   @Value("${jwt.SECRET}")   浮世三千，吾爱有三，日月与卿。日为朝，月为暮，卿为朝朝暮暮。
    private static final String SECRET = "5rWu5LiW5LiJ5Y2D77yM5ZC+54ix5pyJ5LiJ77yM5pel5pyI5LiO5Y2/44CC5pel5Li65pyd77yM5pyI5Li65pqu77yM5Y2/5Li65pyd5pyd5pqu5pqu44CC";
    //密盐
    private static final String ISS = "dmm";
    // 角色的key
    private static final String ROLE_CLAIMS = "rol";
    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 3600L;
    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    // 创建token
    public static String createToken(String username,String role, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);
        map.put("username",username);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)  //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid  之类的，作为什么用户的唯一标志。
                .setClaims(map)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date()) //设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration*1000)) //设置过期时间 ，大于签发时间
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    // 获取用户角色
    public static String getUserRole(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claims.get("rol").toString();
    }

    // 是否已过期
    public static boolean isExpiration(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());

        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private static Claims getTokenBody(String token){
        try {
            final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

        public static void main(String[] args) {
            String name = "lee";
            String role = "ROLE_ADMIN";
            String token = createToken(name,role,false);
            System.out.println(token);

            Claims claims = getTokenBody(token);
            System.out.println(claims.get("username"));
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
            System.out.println("签发时间:"+sdf.format(claims.getIssuedAt()));
            System.out.println("过期时间:"+sdf.format(claims.getExpiration()));
            System.out.println("当前时间:"+sdf.format(new Date()) );

            System.out.println(isExpiration(token));
            System.out.println(getTokenBody(token));

            String ffffff= "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2wiOiJTVVBFUl9BRE1JTiIsInVzZXJuYW1lIjoiYWRtaW4iLCJqdGkiOiJlMGQ3MjE2Ni01ZWNhLTQ3ZDctYTI0Ny1jM2M4MTY3ZDAyNTQiLCJpYXQiOjE2MTU3NzYxMDcsImV4cCI6MTYxNjM4MDkwN30.hoQ7Zx1O1eR-jyt2YZ14iKpIOpDzdR4XKKJcOB2Qu3c";
            System.out.println("vdd"+isExpiration(ffffff));
        }

    }
