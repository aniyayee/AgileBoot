package com.github.domain.user.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yayee
 */
@Getter
@Setter
public class AccountPasswordLoginReq {

    @ApiModelProperty(value = "账号", required = true, position = 1)
    @NotBlank(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "密码", required = true, example = "123456", position = 2)
    @NotBlank(message = "密码不能为空")
    private String password;
}
