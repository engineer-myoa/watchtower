package com.engineering.myoa.watchtower.crawler.scheduler;

import java.util.Arrays;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaCategory;
import com.engineering.myoa.watchtower.crawler.service.ppomppu.OverseaArticleCrawler;

/**
 * OverseaBoardScheduler
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-09
 *
 */
@Component
public class OverseaBoardScheduler {

    private final OverseaArticleCrawler crawler;

    public OverseaBoardScheduler(
            OverseaArticleCrawler crawler) {
        this.crawler = crawler;
    }

    @Scheduled(cron = "0 3/5 * ? * *")
    public void crawl() {
        Arrays.stream(OverseaCategory.values())
              .forEach(crawler::execute);
    }

}
