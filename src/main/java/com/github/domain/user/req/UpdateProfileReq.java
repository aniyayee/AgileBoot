package com.github.domain.user.req;

import lombok.Data;

/**
 * @author yayee
 */
@Data
public class UpdateProfileReq {

    private Long userId;

    private String nickname;
    private String phone;
    private String email;
}
