package com.ymk.health.config.security.service;

import com.ymk.health.entity.SysUser;
import com.ymk.health.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.findUserByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        if (user.isAdmin()) {
            // 管理员获取所有菜单和权限
            user.setMenus(userMapper.findMenus(null));
            user.setPermissions(userMapper.findPermissions(null));
        }
        return user;
    }
}
