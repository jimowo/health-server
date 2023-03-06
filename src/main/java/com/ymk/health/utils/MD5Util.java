package com.ymk.health.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MD5Util {

    /**
     * md5加密
     * md5 加密是单向加密 不可逆
     * BCryptPasswordEncoder 双向加密 是可逆的
     * @param rawPassword 原始密码
     * @return 32位加密串
     */
    public static String md5(String rawPassword) {
        if (StringUtil.isNotEmpty(rawPassword)) {
            byte[] bytes = null;
            try {
                bytes = MessageDigest.getInstance("md5").digest(rawPassword.getBytes());
            } catch (NoSuchAlgorithmException e) {
                log.error("md5错误-->{}", e.getMessage());
            }
            // MD5加密算法得到的字节数组
            StringBuilder code = new StringBuilder(new BigInteger(1, bytes).toString(16));
            // 保证MD5加密后是32位
            for (int i = 0; i < 32 - code.length(); i++) {
                code.insert(0, "0");
            }
            return code.toString();
        } else {
            return null;
        }
    }

    @Test
    public void testMd5() {
        System.out.println(md5("admin"));
    }
}
