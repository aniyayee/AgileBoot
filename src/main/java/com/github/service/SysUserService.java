package com.github.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.core.auth.LoginUser;
import com.github.core.common.req.BulkOperationReq;
import com.github.core.common.resp.PageDTO;
import com.github.domain.user.query.UserQuery;
import com.github.domain.user.req.*;
import com.github.domain.user.resp.UserDTO;
import com.github.entity.SysUser;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author yayee
 * @since 2024-01-31
 */
public interface SysUserService extends IService<SysUser> {

    void addUser(AddUserReq req);

    SysUser getByAccount(String account);

    SysUser getByPhone(String phone);

    SysUser createDefaultUserWithPhone(String phone);

    void deleteUsers(LoginUser loginUser, BulkOperationReq<Long> bulkDeleteReq);

    void updateUser(UpdateUserReq req);

    PageDTO<UserDTO> getUserList(UserQuery query);

    UserDTO getUserInfo(Long userId);

    void resetUserPassword(ResetPasswordReq req);

    void changeUserStatus(ChangeStatusReq req);

    void updateUserProfile(UpdateProfileReq req);

    void updatePasswordBySelf(LoginUser loginUser, UpdateUserPasswordReq req);

    void updateUserAvatar(UpdateUserAvatarReq req);
}
