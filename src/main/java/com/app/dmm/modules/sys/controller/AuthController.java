package com.app.dmm.modules.sys.controller;

import com.app.dmm.modules.user.entity.SysUser;
import com.app.dmm.modules.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register1")
   // public String registerUser(@RequestBody Map<String,String> registerUser){
    public String registerUser(String username,String password ){
        SysUser user = new SysUser(username);
       // user.setUserName(username);
        user.setPassword(bCryptPasswordEncoder.encode(password.trim()));  //强哈希对密码加密
        //user.setUserName(registerUser.get("username"));
        //user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        //user.setSysRoleList("ROLE_USER");
        user.setRoleIds("3");
        sysUserService.save(user);
        return "success";
    }
}

