package com.engineering.myoa.watchtower.crawler.scheduler;

import java.util.Arrays;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaCategory;
import com.engineering.myoa.watchtower.crawler.service.ppomppu.OverseaMulticaster;

/**
 * OverseaMulticastScheduler
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-09
 *
 */
@Component
public class OverseaMulticastScheduler {

    private final OverseaMulticaster multicaster;

    public OverseaMulticastScheduler(
            OverseaMulticaster multicaster) {
        this.multicaster = multicaster;
    }

    @Scheduled(cron = "0 0/5 * ? * *")
    public void multicast() {
        Arrays.stream(OverseaCategory.values())
              .forEach(multicaster::execute);
    }
}
