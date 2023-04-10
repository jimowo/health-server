package com.ymk.health.controller;

import com.ymk.health.utils.AliSmsUtil;
import com.ymk.health.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aliyun")
@Api(value = "阿里云工具接口")
public class AliToolsController {

    @Autowired
    private AliSmsUtil aliSmsUtil;

    @ApiOperation("发送验证码")
    @GetMapping("/captcha/{phone}")
    public Result sendCaptcha(@PathVariable("phone") String phone) {
        return Result.success(aliSmsUtil.sendOneSms(phone));
    }
}
