package com.engineering.myoa.watchtower.crawler.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * AsyncTaskExecutorConfiguration
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Configuration
@EnableAsync(proxyTargetClass = true)
public class AsyncTaskExecutorConfiguration {

    private static final int DEFAULT_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final String DEFAULT_THREAD_NAME_PREFIX = "WT-CRWLR-";

    @Bean("asyncThreadPoolTaskExecutor")
    public Executor asyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(DEFAULT_CORE_POOL_SIZE);
        executor.setMaxPoolSize(DEFAULT_CORE_POOL_SIZE);
        executor.setThreadNamePrefix(DEFAULT_THREAD_NAME_PREFIX);
        executor.initialize();
        return executor;
    }
}
