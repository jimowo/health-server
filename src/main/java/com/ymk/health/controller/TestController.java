package com.ymk.health.controller;


import com.ymk.health.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "测试接口")
public class TestController {

    @ApiOperation(value = "测试test")
    @PreAuthorize("hasAuthority('USER_INSERT')")
    @GetMapping("/test")
    public Result test() {
        return Result.success("信息返回成功", "你好");
    }
}
