package com.github.core.auth;

import com.github.entity.SysUser;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yayee
 */
@Data
@NoArgsConstructor
public class LoginUser {

    public LoginUser(SysUser entity) {
        this.userId = entity.getId();
        this.account = entity.getAccount();
        this.nickname = entity.getNickname();
    }

    private Long userId;
    private String account;
    private String nickname;
}

