package com.engineering.myoa.watchtower.crawler.scheduler;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;

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