package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaArticle;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaMulticastHistory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.PpomppuArticle;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.PpomppuCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.SubscribedOverseaCategory;
import com.engineering.myoa.watchtower.crawler.properties.ApiProperties;
import com.engineering.myoa.watchtower.support.rest.TelegramNotificator;

import lombok.extern.slf4j.Slf4j;

/**
 * OverseaMulticaster
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Slf4j
@Component
public class OverseaMulticaster {

    private static final String PPOMPPU_UNIT_NAME = "ppomppu";

    private final OverseaArticleQueryAdaptor overseaArticleQueryAdaptor;
    private final OverseaMulticastQueryAdaptor overseaMulticastQueryAdaptor;
    private final SubscribedOverseaCategoryQueryAdaptor subscribedOverseaCategoryQueryAdaptor;
    private final TelegramNotificator telegramNotificator;
    private final String ppomppuUrl;

    public OverseaMulticaster(
            OverseaArticleQueryAdaptor overseaArticleQueryAdaptor,
            OverseaMulticastQueryAdaptor overseaMulticastQueryAdaptor,
            SubscribedOverseaCategoryQueryAdaptor subscribedOverseaCategoryQueryAdaptor,
            TelegramNotificator telegramNotificator,
            ApiProperties apiProperties) {
        this.overseaArticleQueryAdaptor = overseaArticleQueryAdaptor;
        this.overseaMulticastQueryAdaptor = overseaMulticastQueryAdaptor;
        this.subscribedOverseaCategoryQueryAdaptor = subscribedOverseaCategoryQueryAdaptor;
        this.telegramNotificator = telegramNotificator;
        this.ppomppuUrl = apiProperties.findByApiName(PPOMPPU_UNIT_NAME).getApiPath();
    }

    @Async("asyncThreadPoolTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(OverseaCategory category) {
        OverseaMulticastHistory history = getMulticastHistory(category);
        log.info("[execute] get history. {}", history);
        List<OverseaArticle> articles = getArticles(category, history);
        if (articles.isEmpty()) {
            log.info("[execute] skipped : {}", category.getDescription());
            return;
        }

        List<String> subscribedUsers = getSubscribedUsers(category);
        multicast(category, articles, subscribedUsers);

        history.update(articles);
        log.info("[execute] history {} updated to articleId: {}", category.getDescription(),
                 history.getArticleId());

        overseaMulticastQueryAdaptor.save(history);
    }

    private OverseaMulticastHistory getMulticastHistory(OverseaCategory category) {
        return overseaMulticastQueryAdaptor.findOneFromCategory(category)
                                           .orElseGet(() -> OverseaMulticastHistory.ofNull(category));
    }

    private List<OverseaArticle> getArticles(OverseaCategory category, OverseaMulticastHistory history) {
        return overseaArticleQueryAdaptor.getArticles(category, history.getArticleId());
    }

    private List<String> getSubscribedUsers(OverseaCategory category) {
        return subscribedOverseaCategoryQueryAdaptor.findByCategory(category)
                                                    .stream()
                                                    .map(SubscribedOverseaCategory::getMemberId)
                                                    .collect(Collectors.toList());
    }

    private <C extends PpomppuCategory, A extends PpomppuArticle> void multicast(C category,
                                                                                 List<A> articles,
                                                                                 List<String> users) {
        String message = toMessage(category, articles);
        users.forEach(user -> telegramNotificator.notifyMessage(user, message));
    }

    // @TODO Create PpomppuArticle factory
    // @TODO Article registered time
    private <C extends PpomppuCategory, A extends PpomppuArticle> String toMessage(C category,
                                                                                   List<A> articles) {
        return String.format("[%s%s]\n", category.getMessagePrefix(), category.getDescription())
               + articles.stream()
                         .map(article -> String.format(">> %s\n"
                                                       + "%s",
                                                       article.getTitle(),
                                                       ppomppuUrl + article.getLink()))
                         .collect(Collectors.joining("\n\n"));
    }
}
