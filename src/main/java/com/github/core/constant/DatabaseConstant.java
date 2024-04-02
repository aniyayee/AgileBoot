package com.github.core.constant;

import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;
import lombok.Getter;

/**
 * @author yayee
 */
public class DatabaseConstant {

    private DatabaseConstant() {
        throw new AgileException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION);
    }

    /**
     * 通用列枚举类
     */
    @Getter
    public enum CommonColumnEnum {

        ID("id"),
        SORT("sort"),
        CREATE_TIME("create_time"),
        UPDATE_TIME("update_time");

        private final String name;

        CommonColumnEnum(String name) {
            this.name = name;
        }
    }

    /**
     * SQL语句枚举类
     */
    @Getter
    public enum SqlEnum {

        LIMIT_1("limit 1"),
        LIMIT_2("limit 2"),
        LIMIT_5("limit 5"),
        LIMIT_30("limit 30"),
        LIMIT_500("limit 500");

        private final String sql;

        SqlEnum(String sql) {
            this.sql = sql;
        }
    }

    /**
     * 用户信息表
     */
    public static class SysUserTable {

        public static final String COLUMN_ACCOUNT = "account";
        public static final String COLUMN_PHONE = "phone";

        private SysUserTable() {
            throw new AgileException(ErrorCode.Business.COMMON_UNSUPPORTED_OPERATION);
        }
    }
}
