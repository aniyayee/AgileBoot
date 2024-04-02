package com.github.service;

import com.github.domain.user.req.AccountPasswordLoginReq;
import com.github.domain.user.req.PhoneCaptchaLoginReq;
import com.github.domain.user.req.UserRegisterReq;
import com.github.domain.user.resp.UserLoginDTO;

/**
 * @author yayee
 */
public interface AccountService {

    UserLoginDTO login(AccountPasswordLoginReq req);

    String generateCaptcha(String phone);

    UserLoginDTO loginByPhone(PhoneCaptchaLoginReq req);
}
