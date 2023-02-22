package com.ymk.health.config.security.service;

import com.ymk.health.entity.SysMenu;
import com.ymk.health.entity.SysUser;
import com.ymk.health.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
            user.setPermissions(userMapper.findPermissions(null));
            user.setRoles(userMapper.findRoles(null));
            List<SysMenu> parentMenus = userMapper.findMenus(null);
            parentMenus.forEach(item -> {
                item.setChildren(userMapper.findChildMenus(null, item.getId()));
            });
            user.setMenus(parentMenus);
        } else {
            List<SysMenu> parentMenus = userMapper.findMenus(user.getId());
            parentMenus.forEach(item -> {
                item.setChildren(userMapper.findChildMenus(user.getId(), item.getId()));
            });
            user.setMenus(parentMenus);
            user.setRoles(userMapper.findRoles(user.getId()));
            user.setPermissions(userMapper.findPermissions(user.getId()));
        }
        return user;
    }
}
