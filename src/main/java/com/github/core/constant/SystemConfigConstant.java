package com.github.core.constant;

import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;

/**
 * @author yayee
 */
public class SystemConfigConstant {

    /**
     * Http 请求认证 Header
     */
    public static final String HTTP_AUTH_HEADER_NAME = "Authorization";
    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "profile";
    /**
     * 默认用户账号 前缀
     */
    public static final String USER_NAME_PREFIX = "user_";

    /**
     * 默认用户密码
     */
    public static final String USER_PASSWORD = "123456";

    /**
     * 用户头像上传路径
     */
    public static final String AVATAR_PATH = "avatar";

    private SystemConfigConstant() {
        throw new AgileException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION);
    }
}
