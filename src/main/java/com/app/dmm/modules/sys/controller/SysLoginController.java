package com.app.dmm.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.app.dmm.annotation.Log;
import com.app.dmm.core.ApiResult;
import com.app.dmm.core.controller.BaseController;
import com.app.dmm.core.utils.JwtTokenUtils;
import com.app.dmm.modules.sys.dto.request.SysLoginVo;
import com.app.dmm.modules.user.entity.SysUser;
import com.app.dmm.modules.user.service.SysUserService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/sys/login")
public class SysLoginController extends BaseController {

    private static Logger logger = LogManager.getLogger(SysLoginController.class);

    @Autowired
    private Producer producer;

    @Autowired
    private SysUserService sysUserService;

    //@Autowired
    //private AuthenticationManager authenticationManager;



    @ApiOperation(value = "系统用户登录")
    @PostMapping(value = "/login.do")
    public ApiResult<JSONObject> login(@RequestBody SysLoginVo userLoginVo, HttpServletRequest request) {
        ApiResult<JSONObject> result = new ApiResult<JSONObject>();
        String username = userLoginVo.getUsername();
        String password = userLoginVo.getPassword();
        //String kaptcha = userLoginVo.getCaptcha();
        //从session中获取之前保存的验证码，跟前台传过来的验证码进行匹配
        //Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //1、校验验证码
       /* if(kaptcha == null) {
            return result.error500("验证码已失效");
        }*/
        /*if(!captcha.equals(kaptcha)) {
            return super.failure("验证码不正确");
        }*/
        //2、 校验用户是否有效
        SysUser sysUser = sysUserService.findByUserName(username);
        if(sysUser == null) {
            return result.error500("账号不存在");
        }
        if(!sysUser.getPassword().equals(password)) {
            return result.error500("密码不正确");
        }
        //账号锁定
        if(sysUser.getState() == 0) {
            return result.error500("账号已被锁定，请联系管理员");
        }
        //3、用户登录信息
        userInfo(sysUser, result);
        //4、添加日志
        //系统登录认证
        //JwtAuthenticationToken token = SecurityUtils.login(request,username,password,authenticationManager);
       // return super.success(token);
        return result;
    }
    @Log(value = "运维平台：调用派发工单接口")
    @ApiOperation(value = "用户退出")
    @RequestMapping(value = "/logout.do",method = {RequestMethod.POST})
    public ApiResult<Object> logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        return super.success();
    }
    /**
     * 用户信息
     *
     * @param sysUser
     * @param result
     * @return
     */
    private ApiResult<JSONObject> userInfo(SysUser sysUser, ApiResult<JSONObject> result) {
        String syspassword = sysUser.getPassword();
        String username = sysUser.getUserName();
        // 生成token
        String token = JwtTokenUtils.createToken(username, syspassword,false);
        // 设置token缓存有效时间
        //redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        //redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);
        JSONObject obj = new JSONObject();
        obj.put("token", token);
        obj.put("userInfo", sysUser);
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }
}
