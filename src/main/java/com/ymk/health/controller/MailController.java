package com.ymk.health.controller;

import com.ymk.health.utils.MailUtil;
import com.ymk.health.utils.Result;
import com.ymk.health.vo.MailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/mail")
@Api(value = "邮件功能接口")
public class MailController {

    @Autowired
    private MailUtil mailUtil;

    @ApiOperation(value = "忘记密码")
    @PostMapping("/forgetPwd")
    public Result forgetPwd(@RequestBody MailVo mail) {
        mail.setSubject("个人健康平台");
        // TODO: 需要提供修改密码的功能
        Random random = new Random();
        mail.setContent("您的新密码: " + random.nextInt() + "请妥善保管");
        return Result.success(mailUtil.sendMail(mail));
    }
}
