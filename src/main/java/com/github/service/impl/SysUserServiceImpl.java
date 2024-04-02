package com.github.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.core.auth.LoginUser;
import com.github.core.cache.CacheCenter;
import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;
import com.github.core.common.req.BulkOperationReq;
import com.github.core.common.resp.PageDTO;
import com.github.core.constant.DatabaseConstant;
import com.github.core.constant.SystemConfigConstant;
import com.github.domain.user.query.UserQuery;
import com.github.domain.user.req.*;
import com.github.domain.user.resp.UserDTO;
import com.github.entity.SysUser;
import com.github.mapper.SysUserMapper;
import com.github.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author yayee
 * @since 2024-01-31
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper userMapper;

    @Override
    public void addUser(AddUserReq req) {
        checkParam(req.getAccount(), req.getPhone(), req.getEmail(), null);
        SysUser entity = BeanUtil.copyProperties(req, SysUser.class);
        String password = DigestUtils.md5DigestAsHex(SystemConfigConstant.USER_PASSWORD.getBytes(StandardCharsets.UTF_8));
        entity.setPassword(password);
        userMapper.insert(entity);
    }

    private void checkParam(String account, String phone, String email, Long userId) {
        if (StrUtil.isNotBlank(account)) {
            checkAccountUnique(account, userId);
        }
        checkPhoneUnique(phone, userId);
        checkEmailUnique(email, userId);
    }

    @Override
    public SysUser getByAccount(String account) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConstant.SysUserTable.COLUMN_ACCOUNT, account)
                .last(DatabaseConstant.SqlEnum.LIMIT_1.getSql());

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser getByPhone(String phone) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConstant.SysUserTable.COLUMN_PHONE, phone)
                .last(DatabaseConstant.SqlEnum.LIMIT_1.getSql());

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser createDefaultUserWithPhone(String phone) {
        String account = SystemConfigConstant.USER_NAME_PREFIX + RandomUtil.randomString(6);
        String password = DigestUtils.md5DigestAsHex(SystemConfigConstant.USER_PASSWORD.getBytes(StandardCharsets.UTF_8));
        SysUser entity = SysUser.builder()
                .account(account)
                .nickname(account)
                .phone(phone)
                .password(password)
                .build();
        userMapper.insert(entity);
        return entity;
    }

    @Override
    public void deleteUsers(LoginUser loginUser, BulkOperationReq<Long> bulkDeleteReq) {
        for (Long id : bulkDeleteReq.getIds()) {
            checkCanBeDelete(id, loginUser);
            userMapper.deleteById(id);
        }
    }

    @Override
    public void updateUser(UpdateUserReq req) {
        checkParam(req.getAccount(), req.getPhone(), req.getEmail(), req.getUserId());
        SysUser entity = loadById(req.getUserId());
        BeanUtil.copyProperties(req, entity, "userId");
        userMapper.updateById(entity);

        CacheCenter.userCache.delete(entity.getId());
    }

    @Override
    public PageDTO<UserDTO> getUserList(UserQuery query) {
        Page<SysUser> page = this.page(query.toPage(), query.toQueryWrapper());
        List<UserDTO> records = page.getRecords().stream().map(UserDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page);
    }

    @Override
    public UserDTO getUserInfo(Long userId) {
        SysUser entity = loadById(userId);
        return new UserDTO(entity);
    }

    @Override
    public void resetUserPassword(ResetPasswordReq req) {
        SysUser entity = loadById(req.getUserId());
        String password = DigestUtils.md5DigestAsHex(req.getPassword().getBytes(StandardCharsets.UTF_8));
        entity.setPassword(password);
        userMapper.updateById(entity);

        CacheCenter.userCache.delete(entity.getId());
    }

    @Override
    public void changeUserStatus(ChangeStatusReq req) {
        SysUser entity = loadById(req.getUserId());
        entity.setStatus(Convert.toInt(req.getStatus()));
        userMapper.updateById(entity);

        CacheCenter.userCache.delete(entity.getId());
    }

    @Override
    public void updateUserProfile(UpdateProfileReq req) {
        checkParam(null, req.getPhone(), req.getEmail(), req.getUserId());
        SysUser entity = loadById(req.getUserId());
        BeanUtil.copyProperties(req, entity, "userId");
        userMapper.updateById(entity);

        CacheCenter.userCache.delete(entity.getId());
    }

    @Override
    public void updatePasswordBySelf(LoginUser loginUser, UpdateUserPasswordReq req) {
        SysUser entity = loadById(req.getUserId());
        String oldPassword = DigestUtils.md5DigestAsHex(req.getOldPassword().getBytes(StandardCharsets.UTF_8));
        if (!entity.getPassword().equals(oldPassword)) {
            throw new AgileException(ErrorCode.Business.LOGIN_WRONG_USER_PASSWORD);
        }
        String newPassword = DigestUtils.md5DigestAsHex(req.getNewPassword().getBytes(StandardCharsets.UTF_8));
        if (entity.getPassword().equals(newPassword)) {
            throw new AgileException(ErrorCode.Business.USER_NEW_PASSWORD_IS_THE_SAME_AS_OLD);
        }
        entity.setPassword(newPassword);
        userMapper.updateById(entity);

        CacheCenter.userCache.delete(entity.getId());
    }

    @Override
    public void updateUserAvatar(UpdateUserAvatarReq req) {
        SysUser entity = loadById(req.getUserId());
        entity.setAvatar(req.getAvatar());
        userMapper.updateById(entity);

        CacheCenter.userCache.delete(entity.getId());
    }

    private void checkCanBeDelete(Long userId, LoginUser loginUser) {
        if (Objects.equals(userId, loginUser.getUserId())) {
            throw new AgileException(ErrorCode.Business.USER_CURRENT_USER_CAN_NOT_BE_DELETE);
        }
    }

    private SysUser loadById(Long userId) {
        SysUser byId = this.getById(userId);
        if (byId == null) {
            throw new AgileException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, userId, "用户");
        }
        return byId;
    }

    public void checkAccountUnique(String account, Long userId) {
        if (isAccountDuplicated(account, userId)) {
            throw new AgileException(ErrorCode.Business.USER_ACCOUNT_IS_NOT_UNIQUE);
        }
    }

    public boolean isAccountDuplicated(String account, Long userId) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account)
                .ne(userId != null, "id", userId);
        return userMapper.exists(queryWrapper);
    }

    public void checkPhoneUnique(String phone, Long userId) {
        if (isPhoneDuplicated(phone, userId)) {
            throw new AgileException(ErrorCode.Business.USER_PHONE_NUMBER_IS_NOT_UNIQUE);
        }
    }

    public boolean isPhoneDuplicated(String phone, Long userId) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone)
                .ne(userId != null, "id", userId);
        return userMapper.exists(queryWrapper);
    }

    public void checkEmailUnique(String email, Long userId) {
        if (isEmailDuplicated(email, userId)) {
            throw new AgileException(ErrorCode.Business.USER_EMAIL_IS_NOT_UNIQUE);
        }
    }

    public boolean isEmailDuplicated(String email, Long userId) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email)
                .ne(userId != null, "id", userId);
        return userMapper.exists(queryWrapper);
    }
}
