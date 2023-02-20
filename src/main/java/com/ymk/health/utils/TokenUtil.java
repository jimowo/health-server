package com.ymk.health.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token 工具类
 * 用于生成Token
 */
@Component
public class TokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.tokenHeader}")
    private String header;

    /**
     * 生成Jwt
     * @param map
     * @return
     */
    private String generateJwt(Map<String, Object> map) {
        return Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 生成token
     * @param details 用户信息
     * @return
     */
    public String generateToken(UserDetails details) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("username", details.getUsername());
        map.put("createTime", new Date());
        return this.generateJwt(map);
    }

    /**
     * 根据token 获取荷载信息
     * @param token token值
     * @return
     */
    public Claims getTokenBody(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据token 得到主体信息
     * @param token token值
     * @return
     */
    public String getUserNameByToken(String token) {
        return (String) this.getTokenBody(token).get("username");
    }

    /**
     * 判断token 是否过期
     * @param token
     * @return
     */
    public boolean isExpired(String token) {
        return this.getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = this.getTokenBody(token);
        return this.generateJwt(claims);
    }
}
