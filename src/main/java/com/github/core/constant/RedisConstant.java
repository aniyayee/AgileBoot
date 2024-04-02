package com.github.core.constant;

import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;

/**
 * @author yayee
 */
public class RedisConstant {

    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 7200L;
    public static final String LOGIN_CAPTCHA_KEY = "login:captcha:";
    public static final Long LOGIN_CAPTCHA_TTL = 180L;

    private RedisConstant() {
        throw new AgileException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION);
    }
}
