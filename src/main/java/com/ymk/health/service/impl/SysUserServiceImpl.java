package com.ymk.health.service.impl;

import com.ymk.health.config.security.service.UserDetailsServiceImpl;
import com.ymk.health.entity.SysUser;
import com.ymk.health.mapper.SysUserMapper;
import com.ymk.health.service.SysUserService;
import com.ymk.health.utils.Result;
import com.ymk.health.utils.TokenUtil;
import com.ymk.health.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtil tokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public Result findAll() {
        log.info("获取用户信息");
        return Result.success("获取用户信息成功", sysUserMapper.findAll());
    }

    @Override
    public SysUser findUserByUsername(String Username) {
        return null;
    }

    @Override
    public Result login(LoginVo loginVo) {
        log.info("1. 开始登录");
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
        log.info("2. 验证账号密码");
        if (null == userDetails || !passwordEncoder.matches(loginVo.getPassword(), userDetails.getPassword())) {
            return Result.fail("账号或密码错误");
        }
        log.info("3. 判断账号是否启用");
        if (!userDetails.isEnabled()) {
            return Result.fail("账号已禁用");
        }
        log.info("4. 登录成功 在security 对象中存入登录者信息");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("5. 根据登录信息获取token");
        // token 使用jwt
        String token = tokenUtil.generateToken(userDetails);
        Map<String, String> map = new HashMap<>(2);
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        return Result.success("登录成功", map);
    }
}
