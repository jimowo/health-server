package com.ymk.health.utils;

import com.ymk.health.vo.MailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * 邮件工具类
 */
@Component
@Slf4j
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件
     * @param mailVo 内容
     * @return 发送结果
     */
    public String sendMail(MailVo mailVo) {
        try {
            if (mailVo.isHtml()) {
                // 发送Html 格式
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setTo(mailVo.getReceiver());
                mimeMessageHelper.setSubject(mailVo.getSubject());
                mimeMessageHelper.setText(mailVo.getContent(), true);
                mailSender.send(mimeMessage);
                log.info("Html邮件发送成功");
                return "Html邮件发送成功";
            } else {
                // 发送纯文本格式
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(from);
                mailMessage.setTo(mailVo.getReceiver());
                mailMessage.setSubject(mailVo.getSubject());
                mailMessage.setText(mailVo.getContent());
                mailSender.send(mailMessage);
                log.info("普通邮件发送成功");
                return "普通邮件发送成功";
            }
        } catch (Exception e) {
            log.error("邮件发送失败-->{}", e.getMessage());
            return "邮件发送失败";
        }
    }
}
