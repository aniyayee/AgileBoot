package com.github.core.cache;

import cn.hutool.extra.spring.SpringUtil;
import com.github.core.auth.LoginUser;
import com.github.core.enums.CacheKeyEnum;
import com.github.core.utils.RedisUtil;
import com.github.entity.SysUser;
import com.github.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * @author yayee
 */
@Component
@RequiredArgsConstructor
public class RedisCacheService {

    private final RedisUtil redisUtil;

    public RedisCacheTemplate<String> captchaRedisCache;
    public RedisCacheTemplate<LoginUser> loginUserRedisCache;
    public RedisCacheTemplate<SysUser> userRedisCache;

    @PostConstruct
    public void init() {
        captchaRedisCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.CAPTCHAT);
        loginUserRedisCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.LOGIN_USER_KEY);
        userRedisCache = new RedisCacheTemplate<SysUser>(redisUtil, CacheKeyEnum.USER_ENTITY_KEY) {
            @Override
            public SysUser getObjectFromDb(Object id) {
                SysUserService userService = SpringUtil.getBean(SysUserService.class);
                return userService.getById((Serializable) id);
            }
        };
    }
}
