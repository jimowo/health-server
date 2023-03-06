package com.ymk.health.controller;

import com.ymk.health.service.SysUserService;
import com.ymk.health.utils.QueryInfo;
import com.ymk.health.utils.RedisUtil;
import com.ymk.health.utils.Result;
import com.ymk.health.utils.SecurityUtil;
import com.ymk.health.vo.LoginVo;
import com.ymk.health.vo.RoleMenusPermissionsVo;
import com.ymk.health.vo.UserRolesVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import netscape.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 登录以及退出相关接口
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户使用接口")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public Result login(@RequestBody LoginVo loginVo) {
        return sysUserService.login(loginVo);
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "查询所有用户")
    public Result findAllSysUser() {
        return sysUserService.findAll();
    }

    @GetMapping("/getInfo")
    @ApiOperation(value = "获取用户基本信息")
    public Result getUserInfo(Principal principal) {
        if (null == principal) {
            return Result.fail("请先登录");
        }
        return Result.success("获取用户信息成功", SecurityUtil.getUser());
    }

    @GetMapping("/logout")
    @ApiOperation(value = "退出登录")
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            redisUtil.delete("userInfo_" + SecurityUtil.getUsername());
            return Result.success("退出登录成功");
        }
        return Result.fail("请先登录");
    }

    @PostMapping("/findPage")
    @ApiOperation("分页查询")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return sysUserService.findPage(queryInfo);
    }

    @PostMapping("/insert")
    @ApiOperation("添加用户")
    public Result insert(@RequestBody UserRolesVo user) {
        return sysUserService.insert(user);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除用户")
    public Result delete(@PathVariable("id") long id) {
        return sysUserService.delete(id);
    }

    @PutMapping("/update")
    @ApiOperation("修改用户")
    public Result update(@RequestBody UserRolesVo user) {
        return sysUserService.update(user);
    }
}
