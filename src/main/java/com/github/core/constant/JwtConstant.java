package com.github.core.constant;

import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;

/**
 * @author yayee
 */
public class JwtConstant {

    public static final String AUTHORIZATION = "Authorization";
    public static final String LOGIN_USER_KEY = "login_user_key";

    private JwtConstant() {
        throw new AgileException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION);
    }
}
