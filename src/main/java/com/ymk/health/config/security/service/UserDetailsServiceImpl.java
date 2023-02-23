package com.ymk.health.config.security.service;

import com.ymk.health.entity.SysMenu;
import com.ymk.health.entity.SysUser;
import com.ymk.health.mapper.SysUserMapper;
import com.ymk.health.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据用户名读取用户数据
     * @param username 用户名
     * @return 用户数据
     * @throws UsernameNotFoundException 用户名不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 判断缓存中是否存在用户信息
        SysUser user;
        if (redisUtil.hasKey("userInfo_" + username)) {
            // 存在则从缓存中直接读取
            user = (SysUser) redisUtil.get("userInfo_" + username);
            // 使用时刷新缓存暂存时间
            redisUtil.setExpire("userInfo_" + username, 300);
            log.info("从Redis 中读取用户信息");
        } else {
            // 否则从数据库中读取用户数据
            user = userMapper.findUserByUsername(username);
            log.info("从数据库中读取用户信息");
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
                // 其他用户
                List<SysMenu> parentMenus = userMapper.findMenus(user.getId());
                parentMenus.forEach(item -> {
                    item.setChildren(userMapper.findChildMenus(user.getId(), item.getId()));
                });
                user.setMenus(parentMenus);
                user.setRoles(userMapper.findRoles(user.getId()));
                user.setPermissions(userMapper.findPermissions(user.getId()));
            }
            // 数据库中读取完数据后 存入redis 缓存
            redisUtil.set("userInfo_" + username, user, 300);
        }
        return user;
    }
}
