package com.github.domain.user.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author yayee
 */
@Getter
@Setter
public class AddUserReq {

    @ApiModelProperty(value = "账号", required = true, position = 1)
    @NotBlank(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "用户昵称", required = true, position = 2)
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "用户邮箱", required = true, example = "123456@163.com", position = 3)
    @NotBlank(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "手机号", required = true, example = "18888888888", position = 1)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]{9}$", message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty("备注")
    private String remark;
}
