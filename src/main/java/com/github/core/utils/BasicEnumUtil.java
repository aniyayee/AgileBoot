package com.github.core.utils;

import cn.hutool.core.convert.Convert;
import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;
import com.github.core.enums.BasicEnum;

import java.util.Objects;

/**
 * @author yayee
 */
public class BasicEnumUtil {

    public static final String UNKNOWN = "未知";

    private BasicEnumUtil() {
    }

    public static <E extends Enum<E>> E fromValueSafely(Class<E> enumClass, Object value) {
        E target = null;

        for (E enumConstant : enumClass.getEnumConstants()) {
            BasicEnum<?> basicEnum = (BasicEnum<?>) enumConstant;
            if (Objects.equals(basicEnum.getValue(), value)) {
                target = (E) basicEnum;
            }
        }

        return target;
    }

    public static <E extends Enum<E>> E fromValue(Class<E> enumClass, Object value) {
        E target = null;

        for (E enumConstant : enumClass.getEnumConstants()) {
            BasicEnum basicEnum = (BasicEnum) enumConstant;
            if (Objects.equals(basicEnum.getValue(), value)) {
                target = (E) basicEnum;
            }
        }

        if (target == null) {
            throw new AgileException(ErrorCode.Internal.GET_ENUM_FAILED, enumClass.getSimpleName());
        }

        return target;
    }

    public static <E extends Enum<E>> String getDescriptionByBool(Class<E> enumClass, Boolean bool) {
        Integer value = Convert.toInt(bool, 0);
        return getDescriptionByValue(enumClass, value);
    }

    public static <E extends Enum<E>> String getDescriptionByValue(Class<E> enumClass, Object value) {
        E basicEnum = fromValueSafely(enumClass, value);
        if (basicEnum != null) {
            return ((BasicEnum<?>) basicEnum).description();
        }
        return UNKNOWN;
    }
}
