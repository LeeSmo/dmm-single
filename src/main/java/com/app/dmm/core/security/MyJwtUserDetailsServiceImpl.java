package com.app.dmm.core.security;

import com.app.dmm.core.security.userdetails.JwtDetailsUser;
import com.app.dmm.modules.sys.entity.SysRole;
import com.app.dmm.modules.sys.service.SysRoleService;
import com.app.dmm.modules.user.entity.SysUser;
import com.app.dmm.modules.user.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MyJwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       //1、查询用户
        SysUser user = sysUserService.findByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in db");
            //这里找不到必须抛异常
        }
        // 2. 设置角色
        //创建集合，用来保存用户权限，GrantedAuthority对象代表赋予当前用户的权限
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //获取当前用户权限集合
        //List<String> rolesList = Arrays.asList(user.getRoleIds().split(","));
        List<SysRole> sysRoleList = sysRoleService.findByUserId(user.getId());
        /*if(CollectionUtil s.isNotEmpty(rolesList)) {
            for(String rol : rolesList) {
                SysRole sysRole = s
            }
        }*/

        String roleIds = null;
        if(CollectionUtils.isNotEmpty(sysRoleList)) {
            for(SysRole role : sysRoleList) {
                //将关联对象SysRole的authority属性保存为用户的认证权限
                authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
            }

            StringBuffer roleids = new StringBuffer();
            for(int i = 0; i<sysRoleList.size();i++){
                if(roleids.length()>0) {      //该步即不会第一位有逗号，也防止最后一位拼接逗号！
                    roleids.append(",");
                }
                roleids.append(sysRoleList.get(i).getRoleCode());
                }
            roleIds = new String(roleids);
        }
        // 此处返回的是 org.springframework.security.core.userdetails.User类
        // springBoot Security 内部的实现
        return new JwtDetailsUser(user,roleIds,authorities);
    }
}
