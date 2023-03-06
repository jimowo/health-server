package com.ymk.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ymk.health.entity.SysRole;
import com.ymk.health.entity.SysUser;
import com.ymk.health.mapper.SysUserMapper;
import com.ymk.health.service.SysUserService;
import com.ymk.health.utils.*;
import com.ymk.health.vo.LoginVo;
import com.ymk.health.vo.UserRolesVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
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
    
    @Autowired
    private RedisUtil redisUtil;

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
        if (null == userDetails || !passwordEncoder.matches(MD5Util.md5(loginVo.getPassword()), userDetails.getPassword())) {
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

    @Transactional
    @Override
    public Result insert(UserRolesVo sysUser) {
        log.info("1. 添加用户");
        // 编码密码 md5加密
        sysUser.setPassword(passwordEncoder.encode(MD5Util.md5(sysUser.getPassword())));
        sysUserMapper.insert(sysUser);
        long userId = sysUserMapper.findUserId(sysUser.getUsername());
        if (sysUser.getRoles() != null && sysUser.getRoles().size() > 0) {
            log.info("2. 绑定用户角色信息");
            sysUser.getRoles().forEach(roleId -> {
                sysUserMapper.bindUserRoles(userId, roleId);
            });
        }
        redisUtil.delete("userInfo_" + SecurityUtil.getUsername());
        return Result.success("添加用户成功");
    }

    @Transactional
    @Override
    public Result delete(long userId) {
        // 需要刪除角色和绑定的菜单权限信息
        sysUserMapper.delete(userId);
        sysUserMapper.deleteUserRoles(userId);
        redisUtil.delete("userInfo_" + SecurityUtil.getUsername());
        return Result.success("删除用户成功");
    }

    @Transactional
    @Override
    public Result update(UserRolesVo sysUser) {
        log.info("1. 更新用户信息");
        sysUser.setPassword(passwordEncoder.encode(MD5Util.md5(sysUser.getPassword())));
        sysUserMapper.update(sysUser);
        if (sysUser.getRoles() != null && sysUser.getRoles().size() > 0) {
            log.info("2. 删除旧的绑定信息");
            sysUserMapper.deleteUserRoles(sysUser.getId());
            sysUser.getRoles().forEach(roleId -> {
                sysUserMapper.bindUserRoles(sysUser.getId(), roleId);
            });

        }
        redisUtil.delete("userInfo_" + SecurityUtil.getUsername());
        return Result.success("修改用户成功");
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始数据分页-->页码{}, -->页数{} , -->查询内容{}",
                queryInfo.getPageNumber(), queryInfo.getPageSize(), queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<SysUser> page = sysUserMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysUser> result = page.getResult();
        // 添加用户角色信息
        result.forEach(user -> {
            user.setRoles(sysUserMapper.findRoles(user.getId()));
        });
        log.info("查询的总条数-->{}", total);
        log.info("分页结果-->{}", result);
        return PageResult.pageSuccess(total, result);
    }
}
