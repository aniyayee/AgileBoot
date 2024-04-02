package com.github.controller;

import com.github.core.auth.LoginUser;
import com.github.core.auth.LoginUserHolder;
import com.github.core.common.base.BaseController;
import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;
import com.github.core.common.resp.ResponseDTO;
import com.github.core.constant.SystemConfigConstant;
import com.github.core.utils.FileUploadUtils;
import com.github.domain.user.req.UpdateProfileReq;
import com.github.domain.user.req.UpdateUserAvatarReq;
import com.github.domain.user.req.UpdateUserPasswordReq;
import com.github.domain.user.resp.UploadFileDTO;
import com.github.domain.user.resp.UserDTO;
import com.github.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yayee
 */
@Api(tags = "个人信息API")
@RestController
@RequestMapping("/system/profile")
@RequiredArgsConstructor
public class SysProfileController extends BaseController {

    private final SysUserService userService;

    /**
     * 个人信息
     */
    @ApiOperation("获取个人信息")
    @GetMapping
    public ResponseDTO<UserDTO> profile() {
        LoginUser loginUser = LoginUserHolder.getCurrentLoginUser();
        UserDTO info = userService.getUserInfo(loginUser.getUserId());
        return ResponseDTO.ok(info);
    }

    /**
     * 修改用户
     */
    @ApiOperation("修改个人信息")
    @PutMapping
    public ResponseDTO<Void> updateProfile(@RequestBody UpdateProfileReq req) {
        LoginUser loginUser = LoginUserHolder.getCurrentLoginUser();
        req.setUserId(loginUser.getUserId());
        userService.updateUserProfile(req);
        return ResponseDTO.ok();
    }

    /**
     * 重置密码
     */
    @ApiOperation("重置个人密码")
    @PutMapping("/password")
    public ResponseDTO<Void> updatePassword(@RequestBody UpdateUserPasswordReq req) {
        LoginUser loginUser = LoginUserHolder.getCurrentLoginUser();
        req.setUserId(loginUser.getUserId());
        userService.updatePasswordBySelf(loginUser, req);
        return ResponseDTO.ok();
    }

    /**
     * 头像上传
     */
    @ApiOperation("修改个人头像")
    @PostMapping("/avatar")
    public ResponseDTO<UploadFileDTO> avatar(@RequestParam("avatarfile") MultipartFile file) {
        if (file.isEmpty()) {
            throw new AgileException(ErrorCode.Business.USER_UPLOAD_FILE_FAILED);
        }
        LoginUser loginUser = LoginUserHolder.getCurrentLoginUser();
        String avatarUrl = FileUploadUtils.upload(SystemConfigConstant.AVATAR_PATH, file);

        userService.updateUserAvatar(new UpdateUserAvatarReq(loginUser.getUserId(), avatarUrl));
        return ResponseDTO.ok(new UploadFileDTO(avatarUrl));
    }
}

