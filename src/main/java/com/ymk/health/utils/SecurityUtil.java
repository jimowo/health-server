package com.ymk.health.utils;

import com.ymk.health.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取当前用户的基本信息
 */
public class SecurityUtil {

    /**
     * 从Security 主体信息中获取用户信息
     * @return
     */
    public static SysUser getUser() {
        SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 不能把密码传回前端
        user.setPassword(null);
        return user;
    }

    /**
     * 从Security 主体信息中获取用户名
     * @return
     */
    public static String getUsername() {
        return getUser().getUsername();
    }

    /**
     * 从Security 主体信息中获取用户Id
     * @return
     */
    public long getUserId() {
        return getUser().getId();
    }
}
