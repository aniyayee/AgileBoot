package com.github.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.core.auth.LoginUser;
import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;
import com.github.core.constant.RedisConstant;
import com.github.core.utils.JwtUtil;
import com.github.domain.user.req.AccountPasswordLoginReq;
import com.github.domain.user.req.PhoneCaptchaLoginReq;
import com.github.domain.user.resp.UserLoginDTO;
import com.github.entity.SysUser;
import com.github.service.AccountService;
import com.github.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yayee
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final StringRedisTemplate stringRedisTemplate;
    private final SysUserService userService;
    private final JwtUtil jwtUtil;

    @Override
    public UserLoginDTO login(AccountPasswordLoginReq req) {
        SysUser entity = userService.getByAccount(req.getAccount());
        if (entity == null || !entity.getPassword().equals(DigestUtils.md5DigestAsHex(req.getPassword().getBytes(StandardCharsets.UTF_8)))) {
            throw new AgileException(ErrorCode.Business.LOGIN_WRONG_USER_PASSWORD);
        }

        String token = createTokenAndPutUserInCache(entity);
        return UserLoginDTO.builder()
                .uid(entity.getId())
                .nickname(entity.getNickname())
                .token(token)
                .build();
    }

    @Override
    public String generateCaptcha(String phone) {
        if (!PhoneUtil.isMobile(phone)) {
            throw new AgileException(ErrorCode.Business.USER_PHONE_FORMAT_ERROR);
        }
        String captcha = RandomUtil.randomString(6);
        stringRedisTemplate.opsForValue().set(RedisConstant.LOGIN_CAPTCHA_KEY + phone, captcha, RedisConstant.LOGIN_CAPTCHA_TTL, TimeUnit.SECONDS);
        return captcha;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserLoginDTO loginByPhone(PhoneCaptchaLoginReq req) {
        String phone = req.getPhone();
        validateCaptcha(phone, req.getCaptcha());
        SysUser entity = userService.getByPhone(phone);
        if (entity == null) {
            entity = userService.createDefaultUserWithPhone(phone);
        }

        String token = createTokenAndPutUserInCache(entity);
        return UserLoginDTO.builder()
                .uid(entity.getId())
                .nickname(entity.getNickname())
                .token(token)
                .build();
    }

    private String createTokenAndPutUserInCache(SysUser entity) {
        LoginUser loginUser = new LoginUser(entity);
        String token = jwtUtil.generateToken(entity.getId());
        Map<String, Object> userMap = BeanUtil.beanToMap(loginUser, new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        String tokenKey = RedisConstant.LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        stringRedisTemplate.expire(tokenKey, RedisConstant.LOGIN_USER_TTL, TimeUnit.SECONDS);
        return token;
    }

    private void validateCaptcha(String phone, String captcha) {
        String cacheCaptcha = stringRedisTemplate.opsForValue().get(RedisConstant.LOGIN_CAPTCHA_KEY + phone);
        stringRedisTemplate.delete(RedisConstant.LOGIN_CAPTCHA_KEY + phone);
        if (cacheCaptcha == null) {
            throw new AgileException(ErrorCode.Business.LOGIN_CAPTCHA_CODE_EXPIRE);
        }
        if (!captcha.equalsIgnoreCase(cacheCaptcha)) {
            throw new AgileException(ErrorCode.Business.LOGIN_CAPTCHA_CODE_WRONG);
        }
    }
}
