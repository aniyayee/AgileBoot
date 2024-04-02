package com.github.domain.user.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @author yayee
 */
@Data
@Builder
public class UserLoginDTO {

    private Long uid;

    private String nickname;

    private String token;
}
