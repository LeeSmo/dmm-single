package com.app.dmm.modules.sys.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**用户凭证对象
 */

public class UserAuthVo implements Serializable {

    private String username;
    private List<SimpleGrantedAuthority> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserAuthVo{" +
                "username='" + username + '\'' +
                ", authorityList=" + authorities +
                '}';
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities;
    }
}