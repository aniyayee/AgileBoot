package com.github.controller;

import com.github.core.auth.LoginUser;
import com.github.core.auth.LoginUserHolder;
import com.github.core.common.base.BaseController;
import com.github.core.common.req.BulkOperationReq;
import com.github.core.common.resp.PageDTO;
import com.github.core.common.resp.ResponseDTO;
import com.github.domain.user.query.UserQuery;
import com.github.domain.user.req.AddUserReq;
import com.github.domain.user.req.ChangeStatusReq;
import com.github.domain.user.req.ResetPasswordReq;
import com.github.domain.user.req.UpdateUserReq;
import com.github.domain.user.resp.UserDTO;
import com.github.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author yayee
 * @since 2024-01-31
 */
@Api(tags = "用户信息接口")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController extends BaseController {

    private final SysUserService userService;

    /**
     * 新增用户
     */
    @ApiOperation("新增用户")
    @PostMapping
    public ResponseDTO<Void> add(@Validated @RequestBody AddUserReq req) {
        userService.addUser(req);
        return ResponseDTO.ok();
    }

    /**
     * 删除用户
     */
    @ApiOperation("删除用户")
    @DeleteMapping("/{userIds}")
    public ResponseDTO<Void> remove(@PathVariable List<Long> userIds) {
        BulkOperationReq<Long> bulkDeleteReq = new BulkOperationReq<>(userIds);
        LoginUser loginUser = LoginUserHolder.getCurrentLoginUser();
        userService.deleteUsers(loginUser, bulkDeleteReq);
        return ResponseDTO.ok();
    }

    /**
     * 修改用户
     */
    @ApiOperation("修改用户")
    @PutMapping("/{userId}")
    public ResponseDTO<Void> edit(@Validated @RequestBody UpdateUserReq req) {
        userService.updateUser(req);
        return ResponseDTO.ok();
    }

    /**
     * 获取用户列表
     */
    @ApiOperation("用户列表")
    @GetMapping
    public ResponseDTO<PageDTO<UserDTO>> userList(UserQuery query) {
        PageDTO<UserDTO> page = userService.getUserList(query);
        return ResponseDTO.ok(page);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @ApiOperation("用户详情")
    @GetMapping("/{userId}")
    public ResponseDTO<UserDTO> getUserInfo(@PathVariable Long userId) {
        UserDTO info = userService.getUserInfo(userId);
        return ResponseDTO.ok(info);
    }

    /**
     * 重置密码
     */
    @ApiOperation("重置用户密码")
    @PutMapping("/{userId}/password")
    public ResponseDTO<Void> resetPassword(@PathVariable Long userId, @RequestBody ResetPasswordReq req) {
        req.setUserId(userId);
        userService.resetUserPassword(req);
        return ResponseDTO.ok();
    }

    /**
     * 状态修改
     */
    @ApiOperation("修改用户状态")
    @PutMapping("/{userId}/status")
    public ResponseDTO<Void> changeStatus(@PathVariable Long userId, @RequestBody ChangeStatusReq req) {
        req.setUserId(userId);
        userService.changeUserStatus(req);
        return ResponseDTO.ok();
    }
}
