package com.ymk.health.controller;

import com.ymk.health.entity.SysMenu;
import com.ymk.health.service.SysMenuService;
import com.ymk.health.utils.QueryInfo;
import com.ymk.health.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@Api(value = "菜单数据接口")
public class SysMenuController {
    
    @Autowired
    private SysMenuService menuService;

    @PostMapping("/findPage")
    @ApiOperation("分页查询")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return menuService.findPage(queryInfo);
    }

    @PostMapping("/insert")
    @ApiOperation("添加菜单")
    public Result insert(@RequestBody SysMenu menu) {
        return menuService.insert(menu);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除菜单")
    public Result delete(@PathVariable("id") long id) {
        return menuService.delete(id);
    }

    @PutMapping("/update")
    @ApiOperation("修改权限")
    public Result update(@RequestBody SysMenu menu) {
        return menuService.update(menu);
    }
}
