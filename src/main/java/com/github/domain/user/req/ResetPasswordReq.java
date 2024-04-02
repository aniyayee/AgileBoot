package com.github.domain.user.req;

import lombok.Data;

/**
 * @author yayee
 */
@Data
public class ResetPasswordReq {

    private Long userId;
    private String password;
}
