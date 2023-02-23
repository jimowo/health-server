package com.ymk.health.controller;

import com.ymk.health.entity.Permission;
import com.ymk.health.service.SysPermissionService;
import com.ymk.health.utils.QueryInfo;
import com.ymk.health.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
@Api(value = "权限数据接口")
public class SysPermissionController {

    @Autowired
    private SysPermissionService permissionService;

    @PostMapping("/findPage")
    @ApiOperation("分页查询")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return permissionService.findPage(queryInfo);
    }

    @PostMapping("/insert")
    @ApiOperation("添加权限")
    public Result insert(@RequestBody Permission permission) {
        return permissionService.insert(permission);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除权限")
    public Result delete(@PathVariable("id") long id) {
        return permissionService.delete(id);
    }

    @PostMapping("/update")
    @ApiOperation("修改权限")
    public Result update(@RequestBody Permission permission) {
        return permissionService.update(permission);
    }
}
