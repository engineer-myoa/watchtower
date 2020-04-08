package com.engineering.myoa.watchtower.crawler.scheduler;

import java.util.Arrays;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.service.ppomppu.DomesticMulticaster;

/**
 * DomesticMulticastScheduler
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Component
public class DomesticMulticastScheduler {

    private final DomesticMulticaster multicaster;

    public DomesticMulticastScheduler(
            DomesticMulticaster multicaster) {
        this.multicaster = multicaster;
    }

    @Scheduled(cron = "0 */2 * ? * *")
    public void multicast() {
        Arrays.stream(DomesticCategory.values())
              .forEach(multicaster::execute);
    }
}
