package com.ymk.health.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 阿里云短信服务
 */
@Component
@Slf4j
public class AliSmsUtil {

    private static String accessKey;

    private static String secretKey;

    @Value("${aliyun.signName}")
    private String signName;

    @Value("${aliyun.templateCode}")
    private String templateCode;

    private static volatile Client smsClient;

    @Value("${aliyun.accessKey}")
    public void setAccessKey(String accessKey) {
        AliSmsUtil.accessKey = accessKey;
    }

    @Value("${aliyun.secretKey}")
    public void setSecretKey(String secretKey) {
        AliSmsUtil.secretKey = secretKey;
    }


    public Client getSmsClient() throws Exception {
        if (smsClient == null) {
            Config config = new com.aliyun.teaopenapi.models.Config()
                    // 必填，您的 AccessKey ID
                    .setAccessKeyId(accessKey)
                    // 必填，您的 AccessKey Secret
                    .setAccessKeySecret(secretKey);
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";
            smsClient = new com.aliyun.dysmsapi20170525.Client(config);
        }
        return smsClient;
    }

    /**
     * 发送单条短信验证码
     * @param phone 接收的手机号码
     * @return 发送状态
     */
    public String sendOneSms(String phone) {
        try {
            // 获取短信Client
            Client client = getSmsClient();
            SendSmsRequest request = new SendSmsRequest()
                    .setTemplateCode("")
                    .setSignName("")
                    .setPhoneNumbers(phone)
                    .setTemplateParam(createSmsTemplate());
            RuntimeOptions runtime = new RuntimeOptions();
            client.sendSmsWithOptions(request, runtime);
            log.info("单条短信发送成功");
            return "单条短信发送成功";
        } catch (Exception e) {
            log.error("单条短信发送失败--> {}", e.getMessage());
            return "单条短信发送失败";
        }
    }

    /**
     * 生成随机验证码
     * @return json格式验证码
     */
    private String createSmsTemplate() {
        Random random = new Random();
        return "{code:" + (1000 + random.nextInt(5000)) + "}";
    }
}
