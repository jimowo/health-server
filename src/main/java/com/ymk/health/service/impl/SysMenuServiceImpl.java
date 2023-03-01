package com.ymk.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymk.health.entity.SysMenu;
import com.ymk.health.mapper.SysMenuMapper;
import com.ymk.health.service.SysMenuService;
import com.ymk.health.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始数据分页-->页码{}, -->页数{} , -->查询内容{}",
                queryInfo.getPageNumber(), queryInfo.getPageSize(), queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<SysMenu> page = menuMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysMenu> result = page.getResult();
        log.info("查询的总条数-->{}", total);
        log.info("分页结果-->{}", result);
        return PageResult.pageSuccess(total, result);
    }

    @Override
    public Result insert(SysMenu menu) {
        menuMapper.insert(menu);
        redisUtil.delete("userInfo_" + SecurityUtil.getUsername());
        return Result.success("添加菜单成功");
    }

    @Override
    public Result delete(long id) {
        menuMapper.delete(id);
        redisUtil.delete("userInfo_" + SecurityUtil.getUsername());
        return Result.success("删除菜单成功");
    }

    @Override
    public Result update(SysMenu menu) {
        menuMapper.update(menu);
        redisUtil.delete("userInfo_" + SecurityUtil.getUsername());
        return Result.success("修改菜单成功");
    }
}

