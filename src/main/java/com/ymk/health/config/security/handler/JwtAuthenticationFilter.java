package com.ymk.health.config.security.handler;

import com.ymk.health.config.security.service.UserDetailsServiceImpl;
import com.ymk.health.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token 校验
 * 在接口访问前进行过滤
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * 请求获取请求头信息
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 获取token
        String header = request.getHeader(this.tokenHeader);
        // 2. 判断token 是否存在
        if (null != header && header.startsWith(tokenHead)) {
            String token = header.substring(tokenHead.length());
            // 根据token 获取用户名
            //TODO: token过期或不存在时 会出现找不到用户名的异常
            String username = tokenUtil.getUserNameByToken(token);
            // 3. token 存在但是没有登录信息
            if (null != token && null == SecurityContextHolder.getContext().getAuthentication()) {
                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
                // 判断token 是否有效
                if (!tokenUtil.isExpired(token) && username.equals(userDetails.getUsername())) {
                    // 刷新security
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // 4. 过滤器放行
        filterChain.doFilter(request, response);
    }
}
