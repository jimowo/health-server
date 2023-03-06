package com.ymk.health.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ymk.health.utils.StringUtil;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class SysUser implements UserDetails {

    private long id;
    private String username;
    private String password;
    private String nickname;
    private Integer sex;
    private String avatar;
    private String address;
    private String wxOpenid;
    private boolean status;
    private boolean admin;
    private String phone;
    private String email;
    private Timestamp createTime;
    private Timestamp updateTime;
    private List<SysRole> roles;
    private List<SysMenu> menus;
    private List<Permission> permissions;

    /**
     * 权限数据
     * @return
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        if (roles != null && roles.size() > 0) {
            roles.forEach(item -> {
                if (StringUtil.isNotEmpty(item.getCode())) {
                    list.add(new SimpleGrantedAuthority(("ROLE_" + item.getCode())));
                }
            });
        }
        if (permissions != null && permissions.size() >0) {
            permissions.forEach(item -> {
                if (StringUtil.isNotEmpty(item.getCode())) {
                    list.add(new SimpleGrantedAuthority(("PER_" + item.getCode())));
                }
            });
        }
        return list;
    }

    /**
     * 账号是否过期
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 账号是否被锁定
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * 当前账号证书是否过期
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return status;
    }
}
