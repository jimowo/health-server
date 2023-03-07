package com.ymk.health.controller;

import com.ymk.health.utils.QiniuUtil;
import com.ymk.health.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/qiniu")
@Api(value = "七牛云服务")
public class QiniuController {

    @Autowired
    private QiniuUtil qiniuUtil;

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public Result upload(@RequestBody MultipartFile file) throws IOException {
        String key = qiniuUtil.uploadQiniu(file.getInputStream(), file.getOriginalFilename());
        return Result.success("文件上传成功", key);
    }
}
