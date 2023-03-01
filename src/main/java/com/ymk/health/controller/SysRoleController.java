package com.ymk.health.controller;

import com.ymk.health.service.SysRoleService;
import com.ymk.health.utils.QueryInfo;
import com.ymk.health.utils.Result;
import com.ymk.health.vo.RoleMenusPermissionsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Api(value = "角色数据接口")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @PostMapping("/findPage")
    @ApiOperation("分页查询")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return roleService.findPage(queryInfo);
    }

    @PostMapping("/insert")
    @ApiOperation("添加角色")
    public Result insert(@RequestBody RoleMenusPermissionsVo role) {
        return roleService.insert(role);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除角色")
    public Result delete(@PathVariable("id") long id) {
        return roleService.delete(id);
    }

    @PutMapping("/update")
    @ApiOperation("修改角色")
    public Result update(@RequestBody RoleMenusPermissionsVo role) {
        return roleService.update(role);
    }
}
