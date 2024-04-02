package com.github.core.common.exception.error;

import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 错误码集合
 *
 * @author yayee
 */
public enum ErrorCode implements ErrorCodeInterface {

    /**
     * 错误码集合
     * ******以下是旧的设计****
     * 1~9999 为保留错误码 或者 常用错误码
     * 10000~19999 为内部错误码
     * 20000~29999 客户端错误码 （客户端异常调用之类的错误）
     * 30000~39999 为第三方错误码 （代码正常，但是第三方异常）
     * 40000~49999 为业务逻辑 错误码 （无异常，代码正常流转，并返回提示给用户）
     * 由于系统内的错误码都是独一无二的，所以错误码应该放在common包集中管理
     * ---------------------------
     * 旧的设计的缺陷，比如内部错误码其实并不会很多  但是占用了1~9999的序列，其实是不必要的。
     * 而且错误码不一定位数一定要相同。比如腾讯的微信接口错误码的位数就并不相同。按照常理错误码的数量大小应该是：
     * 内部错误码< 客户端错误码< 第三方错误码< 业务错误码
     * 所以我们应该尽可能的把错误码的数量留给业务错误码
     * ---------------------------
     * *******新的设计**********
     * 1~99 为内部错误码（框架本身的错误）
     * 100~999 客户端错误码 （客户端异常调用之类的错误）
     * 1000~9999为第三方错误码 （代码正常，但是第三方异常）
     * 10000~99999 为业务逻辑 错误码 （无异常，代码正常流转，并返回提示给用户）
     * 由于系统内的错误码都是独一无二的，所以错误码应该放在common包集中管理
     * ---------------------------
     * 总体设计就是值越小  错误严重性越高
     * 目前10000~19999是初始系统内嵌功能使用的错误码，后续开发者可以直接使用20000以上的错误码作为业务错误码
     */

    SUCCESS(0, "操作成功"), FAILED(99999, "操作失败"),

    HTTP_STATUS_200(200, "ok"),
    HTTP_STATUS_400(400, "request error"),
    HTTP_STATUS_401(401, "no authentication"),
    HTTP_STATUS_403(403, "no authorities"),
    HTTP_STATUS_500(500, "server error");

    public static final List<ErrorCode> HTTP_STATUS_ALL = Collections.unmodifiableList(
            Arrays.asList(HTTP_STATUS_200, HTTP_STATUS_400, HTTP_STATUS_401, HTTP_STATUS_403, HTTP_STATUS_500
            ));

    private final int code;
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.msg;
    }


    /**
     * 10000~99999 为业务逻辑 错误码 （无代码异常，代码正常流转，并返回提示给用户）
     * 1XX01   XX是代表模块的意思 比如10101   01是Permission模块
     * 错误码的命名最好以模块为开头  比如  NOT_ALLOWED_TO_OPERATE前面加上PERMISSION = PERMISSION_NOT_ALLOWED_TO_OPERATE
     */
    public enum Business implements ErrorCodeInterface {

        // ----------------------------- COMMON --------------------------------------

        COMMON_OBJECT_NOT_FOUND(10001, "找不到ID为 {} 的 {}"),

        COMMON_UNSUPPORTED_OPERATION(10002, "不支持的操作"),

        COMMON_BULK_DELETE_IDS_IS_INVALID(10003, "批量参数ID列表为空"),

        COMMON_FILE_NOT_ALLOWED_TO_DOWNLOAD(10004, "文件名称({})非法，不允许下载"),

        // ----------------------------- PERMISSION -----------------------------------

        PERMISSION_FORBIDDEN_TO_MODIFY_ADMIN(10101, "不允许修改管理员的信息"),

        PERMISSION_NOT_ALLOWED_TO_OPERATE(10102, "没有权限进行此操作，请联系管理员"),

        PERMISSION_NOT_YET_LOGIN(10103, "尚未登录，没有权限进行此操作"),

        // ----------------------------- LOGIN -----------------------------------------

        LOGIN_WRONG_USER_PASSWORD(10201, "用户密码错误，请重输"),

        LOGIN_ERROR(10202, "登录失败：{}"),

        LOGIN_CAPTCHA_CODE_WRONG(10203, "验证码错误"),

        LOGIN_CAPTCHA_CODE_EXPIRE(10204, "验证码过期"),

        LOGIN_CAPTCHA_CODE_NULL(10205, "验证码为空"),

        // ----------------------------- UPLOAD -----------------------------------------

        UPLOAD_FILE_TYPE_NOT_ALLOWED(10401, "不允许上传的文件类型，仅允许：{}"),

        UPLOAD_FILE_NAME_EXCEED_MAX_LENGTH(10402, "文件名长度超过：{} "),

        UPLOAD_FILE_SIZE_EXCEED_MAX_SIZE(10403, "文件名大小超过：{} MB"),

        UPLOAD_IMPORT_EXCEL_FAILED(10404, "导入excel失败：{}"),

        UPLOAD_FILE_IS_EMPTY(10405, "上传文件为空"),

        UPLOAD_FILE_FAILED(10406, "上传文件失败：{}"),

        // ----------------------------- CONFIG -----------------------------------------

        CONFIG_VALUE_IS_NOT_ALLOW_TO_EMPTY(10601, "参数键值不允许为空"),

        CONFIG_VALUE_IS_NOT_IN_OPTIONS(10602, "参数键值不存在列表中"),

        // ---------------------------------- USER -----------------------------------------------

        USER_NON_EXIST(10501, "登录用户：{} 不存在"),

        USER_IS_DISABLE(10502, "对不起， 您的账号：{} 已停用"),

        USER_CACHE_IS_EXPIRE(11003, "用户缓存信息已经过期"),

        USER_FAIL_TO_GET_USER_ID(11004, "获取用户ID失败"),

        USER_FAIL_TO_GET_DEPT_ID(10504, "获取用户部门ID失败"),

        USER_FAIL_TO_GET_ACCOUNT(10505, "获取用户账户失败"),

        USER_FAIL_TO_GET_USER_INFO(10506, "获取用户信息失败"),

        USER_IMPORT_DATA_IS_NULL(10507, "导入的用户为空"),

        USER_PHONE_NUMBER_IS_NOT_UNIQUE(10508, "该电话号码已被其他用户占用"),

        USER_EMAIL_IS_NOT_UNIQUE(10509, "该邮件地址已被其他用户占用"),

        USER_PASSWORD_IS_NOT_CORRECT(10510, "用户密码错误"),

        USER_NEW_PASSWORD_IS_THE_SAME_AS_OLD(10511, "用户新密码与旧密码相同"),

        USER_UPLOAD_FILE_FAILED(10512, "用户上传文件失败"),

        USER_ACCOUNT_IS_NOT_UNIQUE(10513, "账户已被其他用户占用"),

        USER_CURRENT_USER_CAN_NOT_BE_DELETE(10514, "当前用户不允许被删除"),

        USER_ADMIN_CAN_NOT_BE_MODIFY(10515, "管理员不允许做任何修改"),

        USER_PHONE_FORMAT_ERROR(10516, "用户手机格式错误"),

        ;


        private final int code;
        private final String msg;

        Business(int code, String msg) {
            Assert.isTrue(code > 10000 && code < 99999, "错误码code值定义失败，Business错误码code值范围在10000~99099之间，请查看ErrorCode.Business类，当前错误码码为" + name());

            this.code = code;
            this.msg = msg;
        }

        @Override
        public int code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.msg;
        }
    }


    /**
     * 1000~9999是外部错误码  比如调用支付失败
     */
    public enum External implements ErrorCodeInterface {

        /**
         * 支付宝调用失败
         */
        FAIL_TO_PAY_ON_ALIPAY(1001, "支付宝调用失败");


        private final int code;
        private final String msg;

        External(int code, String msg) {
            Assert.isTrue(code > 1000 && code < 9999, "错误码code值定义失败，External错误码code值范围在1000~9999之间，请查看ErrorCode.External类，当前错误码码为" + name());

            this.code = code;
            this.msg = msg;
        }

        @Override
        public int code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.msg;
        }
    }


    /**
     * 100~999是客户端错误码
     * 客户端如 Web+小程序+手机端  调用出错
     * 可能由于参数问题或者授权问题或者调用过去频繁
     */
    public enum Client implements ErrorCodeInterface {

        COMMON_FORBIDDEN_TO_CALL(101, "禁止调用"),

        COMMON_REQUEST_TOO_OFTEN(102, "调用太过频繁"),

        COMMON_REQUEST_PARAMETERS_INVALID(103, "请求参数异常，{}"),

        COMMON_REQUEST_METHOD_INVALID(104, "请求方式: {} 不支持"),

        COMMON_REQUEST_RESUBMIT(105, "请求重复提交"),

        COMMON_NO_AUTHORIZATION(106, "请求接口：{} 失败，用户未授权"),

        INVALID_TOKEN(107, "token异常"),

        TOKEN_PROCESS_FAILED(108, "token处理失败：{}"),

        ;

        private final int code;
        private final String msg;

        Client(int code, String msg) {
            Assert.isTrue(code > 100 && code < 999, "错误码code值定义失败，Client错误码code值范围在100~999之间，请查看ErrorCode.Client类，当前错误码码为" + name());

            this.code = code;
            this.msg = msg;
        }

        @Override
        public int code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.msg;
        }
    }


    /**
     * 0~99是内部错误码  例如 框架内部问题之类的
     */
    public enum Internal implements ErrorCodeInterface {
        /**
         * 内部错误码
         */
        INVALID_PARAMETER(1, "参数异常：{}"),

        /**
         * 该错误主要用于返回  未知的异常（大部分是RuntimeException） 程序未能捕获 未能预料的错误
         */
        INTERNAL_ERROR(2, "系统内部错误：{}"),

        GET_ENUM_FAILED(3, "获取枚举类型失败, 枚举类：{}"),

        GET_CACHE_FAILED(4, "获取缓存失败：{}"),

        DB_INTERNAL_ERROR(5, "数据库异常"),

        LOGIN_CAPTCHA_GENERATE_FAIL(7, "验证码生成失败"),

        EXCEL_PROCESS_ERROR(8, "excel处理失败：{}"),

        ;

        private final int code;
        private final String msg;

        Internal(int code, String msg) {
            Assert.isTrue(code < 100, "错误码code值定义失败，Internal错误码code值范围在100~999之间，请查看ErrorCode.Internal类，当前错误码码为" + name());

            this.code = code;
            this.msg = msg;
        }

        @Override
        public int code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.msg;
        }
    }
}
