package com.ymk.health.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@ApiModel(value = "邮件对象")
@Data
public class MailVo implements Serializable {

    @ApiModelProperty(value = "是否为Html格式")
    private boolean isHtml;

    @ApiModelProperty(value = "接收人")
    private String[] receiver;

    @ApiModelProperty(value = "邮件主题")
    private String subject;

    @ApiModelProperty(value = "邮件内容")
    private String content;


}
