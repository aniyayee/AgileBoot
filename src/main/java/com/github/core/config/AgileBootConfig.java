package com.github.core.config;

import com.github.core.constant.SystemConfigConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 读取项目相关配置
 *
 * @author yayee
 */
@Data
@Component
@ConfigurationProperties(prefix = "agileboot")
public class AgileBootConfig {

    /**
     * 上传路径
     */
    private static String fileBaseDir;
    /**
     * 验证码类型
     */
    private static String captchaType;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 版本
     */
    private String version;
    /**
     * 版权年份
     */
    private String copyrightYear;

    public static String getFileBaseDir() {
        return fileBaseDir;
    }

    public void setFileBaseDir(String fileBaseDir) {
        AgileBootConfig.fileBaseDir = fileBaseDir + File.separator + SystemConfigConstant.RESOURCE_PREFIX;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        AgileBootConfig.captchaType = captchaType;
    }
}

