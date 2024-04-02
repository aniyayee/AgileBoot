package com.github.core.common.query;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;

/**
 * @author yayee
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractPageQuery<T> extends AbstractQuery<T> {

    /**
     * 最大分页页数
     */
    public static final int MAX_PAGE_NUM = 200;
    /**
     * 单页最大大小
     */
    public static final int MAX_PAGE_SIZE = 500;
    /**
     * 默认分页页数
     */
    public static final int DEFAULT_PAGE_NUM = 1;
    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    @ApiModelProperty(value = "分页页数", example = "1")
    @Max(MAX_PAGE_NUM)
    protected Integer pageNum;
    @ApiModelProperty(value = "分页大小", example = "10")
    @Max(MAX_PAGE_SIZE)
    protected Integer pageSize;

    public Page<T> toPage() {
        pageNum = ObjectUtil.defaultIfNull(pageNum, DEFAULT_PAGE_NUM);
        pageSize = ObjectUtil.defaultIfNull(pageSize, DEFAULT_PAGE_SIZE);
        return new Page<>(pageNum, pageSize);
    }
}
