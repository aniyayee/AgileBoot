package com.github.core.common.req;

import cn.hutool.core.collection.CollUtil;
import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yayee
 */
@Data
public class BulkOperationReq<T> {

    private Set<T> ids;

    public BulkOperationReq(List<T> idList) {
        if (CollUtil.isEmpty(idList)) {
            throw new AgileException(ErrorCode.Business.COMMON_BULK_DELETE_IDS_IS_INVALID);
        }
        // 移除重复元素
        this.ids = new HashSet<>(idList);
    }
}
