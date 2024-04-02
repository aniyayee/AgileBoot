package com.github.core.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author yayee
 */
@Slf4j
public class LoginUserHolder {

    private static final ThreadLocal<LoginUser> THREAD_LOCAL = new ThreadLocal<>();

    public static void saveCurrentLoginUser(LoginUser user) {
        if (ObjectUtils.isNotEmpty(user)) {
            THREAD_LOCAL.set(user);
            log.debug("ADD THREAD_LOCAL: {}", user);
        }
    }

    public static LoginUser getCurrentLoginUser() {
        LoginUser loginUser = THREAD_LOCAL.get();
        log.debug("GET THREAD_LOCAL: {}", loginUser);
        return loginUser;
    }

    public static void removeCurrentLoginUser() {
        THREAD_LOCAL.remove();
        log.debug("REMOVE THREAD_LOCAL");
    }
}
