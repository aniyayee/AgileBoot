package com.github.core.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.github.core.auth.LoginUser;
import com.github.core.auth.LoginUserHolder;
import com.github.core.constant.JwtConstant;
import com.github.core.constant.RedisConstant;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yayee
 */
@Component
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的token
        String token = request.getHeader(JwtConstant.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            return true;
        }
        // 2.基于token获取redis中的用户
        String tokenKey = RedisConstant.LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
        if (ObjectUtils.isEmpty(userMap)) {
            return true;
        }
        // 3.将查询到的hash数据转为UserDTO
        LoginUser loginUser = BeanUtil.fillBeanWithMap(userMap, new LoginUser(), false);
        // 4.保存用户信息到ThreadLocal
        LoginUserHolder.saveCurrentLoginUser(loginUser);
        // 5.刷新token过期时间
        stringRedisTemplate.expire(tokenKey, RedisConstant.LOGIN_USER_TTL, TimeUnit.SECONDS);
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LoginUserHolder.removeCurrentLoginUser();
    }
}
