package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticArticle;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticMulticastHistory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.SubscribedDomesticCategory;
import com.engineering.myoa.watchtower.crawler.properties.ApiProperties;
import com.engineering.myoa.watchtower.support.rest.TelegramNotificator;

import lombok.extern.slf4j.Slf4j;

/**
 * DomesticMulticaster
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Slf4j
@Component
public class DomesticMulticaster {

    private static final String PPOMPPU_UNIT_NAME = "ppomppu";

    private final DomesticArticleQueryAdaptor domesticArticleQueryAdaptor;
    private final DomesticMulticastQueryAdaptor domesticMulticastQueryAdaptor;
    private final SubscribedDomesticCategoryQueryAdaptor subscribedDomesticCategoryQueryAdaptor;
    private final TelegramNotificator telegramNotificator;
    private final String ppomppuUrl;

    public DomesticMulticaster(
            DomesticArticleQueryAdaptor domesticArticleQueryAdaptor,
            DomesticMulticastQueryAdaptor domesticMulticastQueryAdaptor,
            SubscribedDomesticCategoryQueryAdaptor subscribedDomesticCategoryQueryAdaptor,
            TelegramNotificator telegramNotificator,
            ApiProperties apiProperties) {
        this.domesticArticleQueryAdaptor = domesticArticleQueryAdaptor;
        this.domesticMulticastQueryAdaptor = domesticMulticastQueryAdaptor;
        this.subscribedDomesticCategoryQueryAdaptor = subscribedDomesticCategoryQueryAdaptor;
        this.telegramNotificator = telegramNotificator;
        this.ppomppuUrl = apiProperties.findByApiName(PPOMPPU_UNIT_NAME).getApiPath();
    }

    @Async("asyncThreadPoolTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(DomesticCategory category) {
        DomesticMulticastHistory history = getMulticastHistory(category);
        log.info("[execute] get history. {}", history);
        List<DomesticArticle> articles = getArticles(category, history);

        List<String> subscribedUsers = getSubscribedUsers(category);
        multicast(articles, subscribedUsers);

        history.update(articles);
        log.info("[execute] history {} updated to articleId: {}", category, history.getArticleId());

        domesticMulticastQueryAdaptor.save(history);
    }

    private DomesticMulticastHistory getMulticastHistory(DomesticCategory category) {
        return domesticMulticastQueryAdaptor.findOneFromCategory(category)
                                            .orElseGet(() -> DomesticMulticastHistory.ofNull(category));
    }

    private List<DomesticArticle> getArticles(DomesticCategory category, DomesticMulticastHistory history) {
        return domesticArticleQueryAdaptor.getArticles(category, history.getArticleId());
    }

    private List<String> getSubscribedUsers(DomesticCategory category) {
        return subscribedDomesticCategoryQueryAdaptor.findByCategory(category)
                                                     .stream()
                                                     .map(SubscribedDomesticCategory::getMemberId)
                                                     .collect(Collectors.toList());
    }

    private void multicast(List<DomesticArticle> articles, List<String> users) {
        if (articles.isEmpty()) {
            return;
        }

        String message = toMessage(articles);
        users.forEach(user -> telegramNotificator.notifyMessage(user, message));
    }

    private String toMessage(List<DomesticArticle> articles) {
        return articles.stream()
                       .map(article -> String.format(">> %s\n"
                                                     + "%s",
                                                     article.getTitle(),
                                                     ppomppuUrl + article.getLink()))
                       .collect(Collectors.joining("\n\n"));
    }
}
