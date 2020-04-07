package com.engineering.myoa.watchtower.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.engineering.myoa.watchtower.support.cache.EhCacheConfiguration;
import com.engineering.myoa.watchtower.support.rest.RestTemplateConfiguration;

/**
 * CrawlerApplication
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@EnableScheduling
@SpringBootApplication
@Import({
        RestTemplateConfiguration.class, EhCacheConfiguration.class,
})
public class CrawlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }
}
