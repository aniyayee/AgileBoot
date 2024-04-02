package com.github.domain.user.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.core.common.query.AbstractPageQuery;
import com.github.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yayee
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends AbstractPageQuery<SysUser> {

    private String account;
    private String nickname;
    private String email;
    private String phone;

    @Override
    public QueryWrapper<SysUser> addQueryCondition() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(account), "account", account)
                .eq(StringUtils.isNotEmpty(nickname), "nickname", nickname)
                .eq(StringUtils.isNotEmpty(email), "email", email)
                .eq(StringUtils.isNotEmpty(phone), "phone", phone);

        this.orderColumn = "id";
        this.orderDirection = "descending";
        return queryWrapper;
    }
}
