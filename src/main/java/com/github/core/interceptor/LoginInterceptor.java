package com.github.core.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.github.core.auth.LoginUserHolder;
import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yayee
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (ObjectUtil.isEmpty(LoginUserHolder.getCurrentLoginUser())) {
            throw new AgileException(ErrorCode.Business.PERMISSION_NOT_YET_LOGIN);
        }
        return true;
    }
}
