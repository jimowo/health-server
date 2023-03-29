package com.ymk.health.config.security.contents;

/**
 * 白名单
 */
public class SecurityContent {

    public static final String[] WHITE_LIST = {
            // 后端登录接口
            "/user/login",
            // Swagger
            "/webjars/**",
            "/swagger-ui.html",
            "/doc.html",
            "/swagger-resources/**",
            "/v2/**",
            "/configuration/ui",
            "/configuration/security",
            // 忘记密码接口
            "/mail/forgetPwd/**"
    };
}
