package com.engineering.myoa.watchtower.crawler.scheduler;

import java.util.Arrays;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.service.ppomppu.DomesticArticleCrawler;

/**
 * DomesticBoardScheduler
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Component
public class DomesticBoardScheduler {

    private final DomesticArticleCrawler crawler;

    public DomesticBoardScheduler(
            DomesticArticleCrawler crawler) {
        this.crawler = crawler;
    }

    @Scheduled(cron = "0 3/5 * ? * *")
    public void crawl() {
        Arrays.stream(DomesticCategory.values())
              .forEach(crawler::execute);
    }

}
