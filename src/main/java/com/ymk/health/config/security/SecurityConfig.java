package com.ymk.health.config.security;

import com.ymk.health.config.security.contents.SecurityContent;
import com.ymk.health.config.security.handler.JwtAccessDeniedHandler;
import com.ymk.health.config.security.handler.JwtAuthenticationEntryPoint;
import com.ymk.health.config.security.handler.JwtAuthenticationFilter;
import com.ymk.health.config.security.service.UserDetailsServiceImpl;
import com.ymk.health.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 权限基本配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler accessDeniedHandler;


    /**
     * 配置白名单
     * 白名单：没有权限也可访问的资源（验证码等）
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers(SecurityContent.WHITE_LIST);
    }

    /**
     * Security 的核心配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 1. 使用Jwt 首先关闭跨域攻击
        http.csrf().disable();
        // 2. 关闭session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 3. 设置请求都需要认证 除了白名单
        http.authorizeRequests().anyRequest().authenticated();
        // 4. 关闭缓存
        http.headers().cacheControl();
        // 5. token 过滤器校验
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // 6. 没有登录 没有权限访问资源 自定义返回结果
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
    }

    /**
     * 自定义登录逻辑的配置
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }
}
