package com.github.controller;

import com.github.core.common.base.BaseController;
import com.github.core.common.resp.ResponseDTO;
import com.github.domain.user.req.AccountPasswordLoginReq;
import com.github.domain.user.req.PhoneCaptchaLoginReq;
import com.github.domain.user.resp.UserLoginDTO;
import com.github.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yayee
 */
@Api(value = "登录API", tags = "登录相关接口")
@RestController
@RequiredArgsConstructor
public class AccountController extends BaseController {

    private final AccountService accountService;

    @ApiOperation("账号密码登录")
    @PostMapping("/login")
    public ResponseDTO<UserLoginDTO> login(@RequestBody @Valid AccountPasswordLoginReq req) {
        UserLoginDTO dto = accountService.login(req);
        return ResponseDTO.ok(dto);
    }

    @ApiOperation("验证码")
    @GetMapping("/captcha/{phone}")
    public ResponseDTO<String> getCaptcha(@PathVariable String phone) {
        String captcha = accountService.generateCaptcha(phone);
        return ResponseDTO.ok(captcha);
    }

    @ApiOperation("手机验证码登录")
    @PostMapping("/loginByPhone")
    public ResponseDTO<UserLoginDTO> loginByPhone(@RequestBody @Valid PhoneCaptchaLoginReq req) {
        UserLoginDTO dto = accountService.loginByPhone(req);
        return ResponseDTO.ok(dto);
    }
}
