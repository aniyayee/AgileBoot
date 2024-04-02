package com.github.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.core.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author yayee
 * @since 2024-01-31
 */
@Builder
@Getter
@Setter
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "用户信息表")
public class SysUser extends BaseEntity<SysUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("账户")
    @TableField("account")
    private String account;

    @ApiModelProperty("用户昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("用户邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("手机号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("头像地址")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("密码")
    @TableField("`password`")
    private String password;

    @ApiModelProperty("帐号状态（1正常 2停用 3冻结）")
    @TableField("`status`")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
