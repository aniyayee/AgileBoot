package com.github.domain.user.req;

import lombok.Data;

/**
 * @author yayee
 */
@Data
public class ChangeStatusReq {

    private Long userId;
    private String status;
}
