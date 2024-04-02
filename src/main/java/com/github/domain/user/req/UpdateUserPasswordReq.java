package com.github.domain.user.req;

import lombok.Data;

/**
 * @author yayee
 */
@Data
public class UpdateUserPasswordReq {

    private Long userId;
    private String newPassword;
    private String oldPassword;
}
