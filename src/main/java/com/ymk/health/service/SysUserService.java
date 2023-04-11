package com.ymk.health.service;

import com.ymk.health.entity.SysUser;
import com.ymk.health.utils.QueryInfo;
import com.ymk.health.utils.Result;
import com.ymk.health.vo.LoginVo;
import com.ymk.health.vo.UserRolesVo;

/**
 * 用户操作逻辑的接口
 */
public interface SysUserService {

    /**
     * 获取所有的用户信息
     * @return 用户信息
     */
    Result findAll();

    /**
     * 根据用户名获取用户信息
     * @param Username 用户名
     * @return 用户信息
     */
    SysUser findUserByUsername(String Username);

    /**
     * 根据手机号获取用户信息
     * @param phone 手机号
     * @return 用户信息
     */
    Result findUserByPhone(String phone);

    /**
     * 登录接口
     * @param loginVo 登录信息
     * @return 返回token
     */
    Result login(LoginVo loginVo);

    /**
     * 接收前端信息 添加用户
     * @param sysUser 用户信息
     */
    Result insert(UserRolesVo sysUser);

    /**
     * 根据用户id 删除用户
     * @param userId 用户id
     */
    Result delete(long userId);

    /**
     * 接收前端信息 修改用户信息
     * @param sysUser 用户信息
     */
    Result update(UserRolesVo sysUser);

    /**
     * 前端分页查询
     * @param queryInfo 分页信息
     */
    Result findPage(QueryInfo queryInfo);

}
