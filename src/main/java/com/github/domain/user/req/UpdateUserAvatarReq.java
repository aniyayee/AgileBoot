package com.github.domain.user.req;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yayee
 */
@Data
@AllArgsConstructor
public class UpdateUserAvatarReq {

    private Long userId;
    private String avatar;
}
