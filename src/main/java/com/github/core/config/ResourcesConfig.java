package com.github.core.config;

import com.github.core.constant.SystemConfigConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yayee
 */
@Configuration
@RequiredArgsConstructor
public class ResourcesConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /* 本地文件上传路径 */
        registry.addResourceHandler("/" + SystemConfigConstant.RESOURCE_PREFIX + "/**")
                .addResourceLocations("file:" + AgileBootConfig.getFileBaseDir() + "/");
    }
}
