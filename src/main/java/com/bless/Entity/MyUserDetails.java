package com.bless.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by wangxi on 18/8/8.
 */
@Data
public class MyUserDetails implements UserDetails {
    private String userName;
    private String passWord;
    private ArrayList<SimpleGrantedAuthority> roles;
    private  boolean enabled;
    private  Date lastPasswordResetDate;

    public MyUserDetails(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }

    public MyUserDetails(String userName, String passWord,ArrayList<SimpleGrantedAuthority> roles){
        this.userName = userName;
        this.passWord = passWord;
        this.roles = roles;
    }

    public MyUserDetails(){

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.passWord;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
}
