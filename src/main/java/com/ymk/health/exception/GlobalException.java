package com.ymk.health.exception;

import com.ymk.health.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public Result exception(UsernameNotFoundException e) {
        log.error("用户名不存在--->{}", e.getMessage());
        return Result.fail(("用户名或密码错误"));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class) // 设置捕获的异常类
    public Result exception(AccessDeniedException e) {
        log.error("权限不足--->{}", e.getMessage());
        return Result.fail("权限不足，请联系管理员");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    public Result exception(RuntimeException e) {
        e.printStackTrace();
        log.error("系统运行时异常--->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }
}
