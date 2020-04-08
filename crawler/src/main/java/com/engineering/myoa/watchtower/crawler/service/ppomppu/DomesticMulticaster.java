package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticArticle;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticMulticastHistory;
import com.engineering.myoa.watchtower.support.rest.TelegramNotificator;

/**
 * DomesticMulticaster
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Component
public class DomesticMulticaster {

    private final DomesticArticleQueryAdaptor domesticArticleQueryAdaptor;
    private final DomesticMulticastQueryAdaptor domesticMulticastQueryAdaptor;
    private final TelegramNotificator telegramNotificator;

    public DomesticMulticaster(
            DomesticArticleQueryAdaptor domesticArticleQueryAdaptor,
            DomesticMulticastQueryAdaptor domesticMulticastQueryAdaptor,
            TelegramNotificator telegramNotificator) {
        this.domesticArticleQueryAdaptor = domesticArticleQueryAdaptor;
        this.domesticMulticastQueryAdaptor = domesticMulticastQueryAdaptor;
        this.telegramNotificator = telegramNotificator;
    }

    @Async("asyncThreadPoolTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(DomesticCategory category) {
        DomesticMulticastHistory history = getMulticastHistory(category);
        List<DomesticArticle> articles = getArticles(category, history);
        multicast(category, articles);
        history.update(articles);
        domesticMulticastQueryAdaptor.save(history);
    }

    private DomesticMulticastHistory getMulticastHistory(DomesticCategory category) {
        return domesticMulticastQueryAdaptor.findOneFromCategory(category)
                                            .orElseGet(() -> DomesticMulticastHistory.ofNull(category));
    }

    private List<DomesticArticle> getArticles(DomesticCategory category, DomesticMulticastHistory history) {
        return domesticArticleQueryAdaptor.getArticles(category, history.getArticleId());
    }

    private void multicast(DomesticCategory category, List<DomesticArticle> articles) {

        // TODO get botToken from database
        // TODO get chatId that subscribed category
        // TODO generate concatenated message
        // TODO notify message
        // telegramNotificator.notifyMessage(botToken, chatId, message);
    }

    private String toMessage(List<DomesticArticle> articles) {
        return null;
    }
}
