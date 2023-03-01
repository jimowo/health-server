package com.ymk.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymk.health.entity.SysRole;
import com.ymk.health.mapper.SysRoleMapper;
import com.ymk.health.service.SysRoleService;
import com.ymk.health.utils.*;
import com.ymk.health.vo.RoleMenusPermissionsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始数据分页-->页码{}, -->页数{} , -->查询内容{}",
                queryInfo.getPageNumber(), queryInfo.getPageSize(), queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<SysRole> page = roleMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysRole> result = page.getResult();
        // 获取角色对应的子菜单
        result.forEach(item -> {
            item.getMenus().forEach(menu -> {
                menu.setChildren(roleMapper.findChildMenus(item.getId(), menu.getId()));
            });
        });
        log.info("查询的总条数-->{}", total);
        log.info("分页结果-->{}", result);
        return PageResult.pageSuccess(total, result);
    }

    @Transactional
    @Override
    public Result insert(RoleMenusPermissionsVo role) {
        log.info("1. 添加角色");
        roleMapper.insertByParam(role);
        int roleId = roleMapper.findRoleId(role.getCode());
        if (role.getMenus() != null && role.getMenus().size() > 0) {
            log.info("2. 绑定角色菜单信息");
            role.getMenus().forEach(item -> {
                roleMapper.bindRoleMenus(roleId, item);
            });

        }
        if (role.getPermissions() != null && role.getPermissions().size() > 0) {
            log.info("3. 绑定角色权限信息");
            role.getPermissions().forEach(item -> {
                roleMapper.bindRolePermissions(roleId, item);
            });
        }
        redisUtil.delete("userInfo_" + SecurityUtil.getUsername());
        return Result.success("添加角色成功");
    }

    @Transactional
    @Override
    public Result delete(long id) {
        // 需要刪除角色和绑定的菜单权限信息
        roleMapper.delete(id);
        roleMapper.deleteRoleMenus(id);
        roleMapper.deleteRolePermissions(id);
        redisUtil.delete("userInfo_" + SecurityUtil.getUsername());
        return Result.success("删除角色成功");
    }

    @Transactional
    @Override
    public Result update(RoleMenusPermissionsVo role) {
        log.info("1. 更新角色信息");
        roleMapper.updateByParam(role);
        if (role.getMenus() != null && role.getMenus().size() > 0) {
            log.info("2. 删除旧的绑定信息");
            roleMapper.deleteRoleMenus(role.getId());
            role.getMenus().forEach(item -> {
                roleMapper.bindRoleMenus(role.getId(), item);
            });

        }
        if (role.getPermissions() != null && role.getPermissions().size() > 0) {
            roleMapper.deleteRolePermissions(role.getId());
            role.getPermissions().forEach(item -> {
                roleMapper.bindRolePermissions(role.getId(), item);
            });
        }
        redisUtil.delete("userInfo_" + SecurityUtil.getUsername());
        return Result.success("修改角色成功");
    }
}
