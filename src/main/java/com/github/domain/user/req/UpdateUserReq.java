package com.github.domain.user.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yayee
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateUserReq extends AddUserReq {

    private Long userId;
}
