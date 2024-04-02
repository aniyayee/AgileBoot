package com.github.domain.user.resp;

import com.github.core.cache.CacheCenter;
import com.github.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author yayee
 */
@Data
public class UserDTO {

    public UserDTO(SysUser entity) {
        if (entity != null) {
            this.userId = entity.getId();
            this.account = entity.getAccount();
            this.nickname = entity.getNickname();
            this.email = entity.getEmail();
            this.phone = entity.getPhone();
            this.avatar = entity.getAvatar();
            this.password = entity.getPassword();
            this.status = entity.getStatus();
            this.remark = entity.getRemark();
            this.creatorId = entity.getCreatorId();
            this.createTime = entity.getCreateTime();
            this.updaterId = entity.getUpdaterId();
            this.updateTime = entity.getUpdateTime();

            SysUser creator = CacheCenter.userCache.getObjectById(entity.getCreatorId());
            if (creator != null) {
                this.creatorName = creator.getNickname();
            }
            SysUser updater = CacheCenter.userCache.getObjectById(entity.getUpdaterId());
            if (updater != null) {
                this.updaterName = updater.getNickname();
            }
        }
    }

    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("用户昵称")
    private String nickname;
    @ApiModelProperty("用户邮箱")
    private String email;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("头像地址")
    private String avatar;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("帐号状态（1正常 2停用 3冻结）")
    private Integer status;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("创建者ID")
    private Long creatorId;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新者ID")
    private Long updaterId;
    @ApiModelProperty("更新时间")
    private Date updateTime;

    private String creatorName;
    private String updaterName;
}
