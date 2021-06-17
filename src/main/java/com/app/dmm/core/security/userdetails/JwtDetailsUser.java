package com.app.dmm.core.security.userdetails;

import com.app.dmm.modules.sys.entity.SysRole;
import com.app.dmm.modules.user.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 定制的UserDetail对象
 *
 * @author lee 2020/11/06
 */
public class JwtDetailsUser implements UserDetails {

    //用户数据
    private String username;

    private String password;

    private String language;     //国际语言

    private String roleIds;

    // spring_security验证角色编码 存放权限的集合

    private Collection<? extends GrantedAuthority> authorities;


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    // 能直接使用 user创建 CustomUserDetails的构造器
    public JwtDetailsUser(SysUser user) {

        username      =       user.getUserName();
        password      =       user.getPassword();
        authorities   =       Collections.singleton(new SimpleGrantedAuthority(user.getRoleIds()));
    }
    // 能直接使用 user创建 CustomUserDetails的构造器
    public JwtDetailsUser(SysUser user,String roleIds, Collection<? extends GrantedAuthority> authorities) {

        this.username      =       user.getUserName();
        this.password      =       user.getPassword();
        this.roleIds      =        roleIds;
        this.authorities   =       authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*List<GrantedAuthority> auths = new ArrayList<>();
        List<SysRole> roles = this.getRoles();
        for (SysRole role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getRoleCode()));
        }*/
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtDetailsUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", language='" + language + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
