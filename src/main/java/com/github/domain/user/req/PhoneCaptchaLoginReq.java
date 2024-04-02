package com.github.domain.user.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author yayee
 */
@Getter
@Setter
public class PhoneCaptchaLoginReq {

    @ApiModelProperty(value = "手机号", required = true, example = "18888888888", position = 1)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]{9}$", message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty(value = "验证码", required = true, example = "123456", position = 2)
    @NotBlank(message = "验证码不能为空")
    private String captcha;
}
