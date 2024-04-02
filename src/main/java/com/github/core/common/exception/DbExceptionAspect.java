package com.github.core.common.exception;

import cn.hutool.core.map.MapUtil;
import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @author yayee
 */
@Aspect
@Component
@Slf4j
public class DbExceptionAspect {

    @Pointcut("execution(* com.github.mapper.*.*(..)) || execution(* com.github.service..*.*(..))")
    public void dbException() {
    }

    /**
     * 包装成AgileException 再交给globalExceptionHandler处理
     *
     * @param joinPoint joinPoint
     * @return object
     */
    @Around("dbException()")
    public Object aroundDbException(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        try {
            // 将应用层的数据库错误 捕获并进行转换  主要捕获 sql形式的异常
            proceed = joinPoint.proceed();
        } catch (AgileException e) {
            throw e;
        } catch (Exception sqlException) {
            AgileException wrapException = new AgileException(sqlException, ErrorCode.Internal.DB_INTERNAL_ERROR);
            wrapException.setPayload(MapUtil.of("detail", sqlException.getMessage()));
            throw wrapException;
        }
        return proceed;
    }
}
