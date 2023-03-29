package com.ymk.health.controller;

import com.ymk.health.utils.MailUtil;
import com.ymk.health.utils.Result;
import com.ymk.health.vo.MailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@Api(value = "邮件功能接口")
public class MailController {

    @Autowired
    private MailUtil mailUtil;

    @ApiOperation(value = "忘记密码")
    @GetMapping("/forgetPwd/{mail}")
    public Result forgetPwd(@PathVariable("mail") String mail) {
        MailVo mailVo = new MailVo();
        mailVo.setSubject("个人健康平台 密码重置");
        mailVo.setReceiver(new String[]{mail});
        mailVo.setHtml(true);
        mailVo.setContent(
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>个人运动平台</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>忘记密码</h1>\n" +
                "<p>此处应有一个链接</p>\n" +
                "</body>\n" +
                "</html>"
        );
        return Result.success(mailUtil.sendMail(mailVo));
    }
}
