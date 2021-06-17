package com.app.dmm.modules.user.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.app.dmm.core.ApiResult;
import com.app.dmm.core.controller.BaseController;
import com.app.dmm.core.enums.ResultCode;
import com.app.dmm.core.exception.CustomException;
import com.app.dmm.core.utils.ConvertUtils;
import com.app.dmm.core.utils.MethodUtil;
import com.app.dmm.core.utils.RedisUtil;
import com.app.dmm.modules.user.entity.SysUser;
import com.app.dmm.modules.user.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.app.dmm.core.entity.BaseEntity.DELETE_FLAG_N;
import static com.app.dmm.core.entity.BaseEntity.USER_UNFREEZE;

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    private static Logger logger = LogManager.getLogger(SysUserController.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/save.do")
    public ApiResult<String> exeSaveUser(SysUser user) {
        int i = sysUserService.save(user);
        System.err.println("执行用户保存"+i);
        if(i == 1) {
            return super.success("保存成功");
        }
        return super.message(1,"保存失败",user.getUserName());
    }

    @PostMapping("/edit.do")
    public ApiResult<String> exeUpdateUser(SysUser user) {
        if(user.getId() == null) {
            throw new  CustomException(ResultCode.PARAM_IS_INVALID, MethodUtil.getLineInfo());
        }
        return ApiResult.OK();
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/delete/{id}")
    public ApiResult exeDeleteUser(@PathVariable("id") Long id) {
        sysUserService.deleteById(id);
        return ApiResult.OK();
    }


    @GetMapping(value="/findUser.do")
    public ApiResult<SysUser> exeFindUser(Long id) {
        SysUser user = sysUserService.selectByID(id);
        if(user != null) {
            return super.success(user);
        }else {
            //throw new NullPointerException();
        }
        return super.failure(ResultCode.USER_NOT_EXIST,user);
    }

    @GetMapping(value="/findAllUser.do")
    public ApiResult<List<SysUser>> exeFindAllUser() {
        List<SysUser> userList = sysUserService.findAll();
        return super.message(0,"查询成功",userList);
    }

    @GetMapping(value="/findAll3")
    public ApiResult<SysUser> findAll3() {
        SysUser sysUser = new SysUser();
        if(sysUser == null) {
            throw new NullPointerException();
        }
        sysUser.setRealName("ss");
        return super.failure(ResultCode.NULL_POINT_EXCEPTION,sysUser);
    }
    /**
     * 用户注册接口
     *
     * @param jsonObject
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ApiResult<JSONObject> userRegister(@RequestBody JSONObject jsonObject, SysUser user) {
        ApiResult<JSONObject> result = new ApiResult<JSONObject>();
        String phone = jsonObject.getString("phone");
        String smscode = jsonObject.getString("smscode");
        Object code = redisUtil.get(phone);
        String username = jsonObject.getString("username");
        //未设置用户名，则用手机号作为用户名
        if(StringUtils.isEmpty(username)){
            username = phone;
        }
        //未设置密码，则随机生成一个密码
        String password = jsonObject.getString("password");
        if(StringUtils.isEmpty(password)){
            password = RandomUtil.randomString(8);
        }
        String email = jsonObject.getString("email");
        SysUser sysUser1 = sysUserService.findByUserName(username);
        if (sysUser1 != null) {
            result.setMessage("用户名已注册");
            result.setSuccess(false);
            return result;
        }
       /* SysUser sysUser2 = sysUserService.getUserByPhone(phone);
        if (sysUser2 != null) {
            result.setMessage("该手机号已注册");
            result.setSuccess(false);
            return result;
        }

        if(StringUtils.isNotEmpty(email)){
            SysUser sysUser3 = sysUserService.getUserByEmail(email);
            if (sysUser3 != null) {
                result.setMessage("邮箱已被注册");
                result.setSuccess(false);
                return result;
            }
        }*/
        if(null == code){
            result.setMessage("手机验证码失效，请重新获取");
            result.setSuccess(false);
            return result;
        }
        if (!smscode.equals(code.toString())) {
            result.setMessage("手机验证码错误");
            result.setSuccess(false);
            return result;
        }

        try {
            user.setCreateTime(new Date());// 设置创建时间
            String salt = ConvertUtils.randomGen(8);
            //String passwordEncode = PasswordUtil.encrypt(username, password, salt);
            user.setSalt(salt);
            user.setUserName(username);
            user.setRealName(username);
            //user.setPassword(passwordEncode);
            user.setEmail(email);
            user.setPhoneNumber(phone);
            user.setState(USER_UNFREEZE);
            user.setDeleteFlag(DELETE_FLAG_N);
            //sysUserService.addUserWithRole(user,"ee8626f80f7c2619917b6236f3a7f02b");//默认临时角色 test
            result.success("注册成功");
        } catch (Exception e) {
            result.error500("注册失败");
        }
        return result;
    }
}

