package com.github.core.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 确保应用退出时能关闭后台线程
 *
 * @author yayee
 */
@Component
@Slf4j
public class ShutdownHook {

    @PreDestroy
    public void destroy() {
        shutdownAllThreadPool();
    }

    /**
     * 停止异步执行任务
     */
    private void shutdownAllThreadPool() {
        try {
            log.info("close thread pool");
            ThreadPoolManager.shutdown();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}