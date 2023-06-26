package com.ymk.health.controller;

import com.ymk.health.utils.OpenAiUtil;
import com.ymk.health.utils.Result;
import com.ymk.health.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@Api(value = "ChatGPT对话")
public class OpenAiChatController {

    @Autowired
    private OpenAiUtil openAiUtil;

    @ApiOperation(value = "单个问题提问接口")
    @GetMapping("/question")
    public Result openAiChat(@RequestParam("question") String question) {
        if (!StringUtil.isNotEmpty(question)) {
            return Result.fail("问题为空");
        }
        return Result.success("提问成功", openAiUtil.chat(question));
    }

}
