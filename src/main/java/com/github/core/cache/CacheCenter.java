package com.github.core.cache;

import cn.hutool.extra.spring.SpringUtil;
import com.github.core.auth.LoginUser;
import com.github.entity.SysUser;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 缓存中心  提供全局访问点
 *
 * @author yayee
 */
@Component
public class CacheCenter {
    public static RedisCacheTemplate<String> captchaCache;
    public static RedisCacheTemplate<LoginUser> loginUserCache;
    public static RedisCacheTemplate<SysUser> userCache;

    @PostConstruct
    public void init() {
        RedisCacheService redisCache = SpringUtil.getBean(RedisCacheService.class);

        captchaCache = redisCache.captchaRedisCache;
        loginUserCache = redisCache.loginUserRedisCache;
        userCache = redisCache.userRedisCache;
    }
}
