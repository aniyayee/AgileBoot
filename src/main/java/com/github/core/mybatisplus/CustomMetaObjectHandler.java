package com.github.core.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.core.auth.LoginUser;
import com.github.core.auth.LoginUserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author yayee
 */
@Component
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {

    public static final String CREATE_TIME_FIELD = "createTime";
    public static final String CREATOR_ID_FIELD = "creatorId";

    public static final String UPDATE_TIME_FIELD = "updateTime";
    public static final String UPDATER_ID_FIELD = "updaterId";


    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter(CREATE_TIME_FIELD)) {
            this.setFieldValByName(CREATE_TIME_FIELD, new Date(), metaObject);
        }

        Long userId = getUserIdSafely();
        if (metaObject.hasSetter(CREATOR_ID_FIELD) && userId != null) {
            this.strictInsertFill(metaObject, CREATOR_ID_FIELD, Long.class, getUserIdSafely());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_TIME_FIELD)) {
            this.setFieldValByName(UPDATE_TIME_FIELD, new Date(), metaObject);
        }

        Long userId = getUserIdSafely();
        if (metaObject.hasSetter(UPDATER_ID_FIELD) && userId != null) {
            this.strictUpdateFill(metaObject, UPDATER_ID_FIELD, Long.class, getUserIdSafely());
        }
    }

    public Long getUserIdSafely() {
        Long userId = null;
        try {
            LoginUser loginUser = LoginUserHolder.getCurrentLoginUser();
            userId = loginUser.getUserId();
        } catch (Exception e) {
            log.warn("can not find user in current thread.");
        }
        return userId;
    }
}
