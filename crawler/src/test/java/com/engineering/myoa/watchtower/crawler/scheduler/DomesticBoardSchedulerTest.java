package com.engineering.myoa.watchtower.crawler.scheduler;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SyncTaskExecutor;

import com.engineering.myoa.watchtower.crawler.CrawlerApplication;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.service.ppomppu.DomesticArticleCrawler;

/**
 * DomesticBoardSchedulerTest
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DomesticBoardSchedulerTest {

    @Configuration
    @Import(CrawlerApplication.class)
    static class ContextConfiguration {
        @Bean("asyncThreadPoolTaskExecutor")
        @Primary
        public Executor syncTaskExecutor() {
            return new SyncTaskExecutor();
        }
    }

    @Autowired
    DomesticArticleCrawler crawler;

    @Autowired
    @Qualifier("ehCacheManager")
    CacheManager cacheManager;

    @Test
    @Ignore
    void crawl() throws IOException {
        Arrays.stream(DomesticCategory.values())
              .forEach(crawler::execute);
    }
}