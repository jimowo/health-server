package com.ymk.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymk.health.entity.Permission;
import com.ymk.health.mapper.SysPermissionMapper;
import com.ymk.health.service.SysPermissionService;
import com.ymk.health.utils.PageResult;
import com.ymk.health.utils.QueryInfo;
import com.ymk.health.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始数据分页-->页码{}, -->页数{} , -->查询内容{}",
                queryInfo.getPageNumber(), queryInfo.getPageSize(), queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Permission> page = permissionMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<Permission> result = page.getResult();
        log.info("查询的总条数-->{}", total);
        log.info("分页结果-->{}", result);
        return PageResult.pageSuccess(total, result);
    }

    @Override
    public Result insert(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success("添加权限成功");
    }

    @Override
    public Result delete(long id) {
        permissionMapper.delete(id);
        return Result.success("删除权限成功");
    }

    @Override
    public Result update(Permission permission) {
        permissionMapper.update(permission);
        return Result.success("修改权限成功");
    }
}
