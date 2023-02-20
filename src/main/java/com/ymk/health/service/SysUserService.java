package com.ymk.health.service;

import com.ymk.health.entity.SysUser;
import com.ymk.health.utils.Result;
import com.ymk.health.vo.LoginVo;

/**
 * 用户操作逻辑的接口
 */
public interface SysUserService {

    /**
     * 获取所有的用户信息
     * @return
     */
    Result findAll();

    /**
     * 根据用户名获取用户信息
     * @param Username 用户名
     * @return
     */
    SysUser findUserByUsername(String Username);

    /**
     * 登录接口
     * @param loginVo 登录信息
     * @return 返回token
     */
    Result login(LoginVo loginVo);
}
