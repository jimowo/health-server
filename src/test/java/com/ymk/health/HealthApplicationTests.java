package com.ymk.health;

import com.ymk.health.utils.MailUtil;
import com.ymk.health.vo.MailVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HealthApplicationTests {

    @Autowired
    private MailUtil mailUtil;

    @Test
    void contextLoads() {
    }

    @Test
    void testMail() {
        MailVo mail = new MailVo();
        mail.setReceiver(new String[]{"1252480844@qq.com"});
        mail.setSubject("个人运动平台");
        mail.setContent(
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>个人运动平台</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>邮件测试</h1>\n" +
                "<p>html邮件测试。</p>\n" +
                "</body>\n" +
                "</html>"
        );
        mail.setHtml(true);
        System.out.println(mailUtil.sendMail(mail));
    }

}
