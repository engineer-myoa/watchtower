package com.engineering.myoa.watchtower.crawler.doamin.ppomppu;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * DomesticMulticastHistoryTest
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@ExtendWith(SpringExtension.class)
class DomesticMulticastHistoryTest {
    private static final Long NOT_UPDATED_ARTICLE_ID = 0L;

    @Test
    void update_when_given_single_article_with_notmatched_category() {
        // given
        final DomesticCategory category = DomesticCategory.COMPUTER;
        DomesticMulticastHistory histrory = DomesticMulticastHistory.ofNull(category);
        DomesticArticle article = DomesticArticle.builder().articleId(5L).build();

        // when
        histrory.update(article);

        // then
        Assertions.assertThat(histrory.getArticleId())
                  .isEqualTo(NOT_UPDATED_ARTICLE_ID);
    }

    @Test
    void update_when_given_articles_with_notmatched_category() {
        // given
        final DomesticCategory category = DomesticCategory.COMPUTER;
        final Long MAX_ARTICLE_ID = 10L;
        DomesticMulticastHistory histrory = DomesticMulticastHistory.ofNull(category);
        List<DomesticArticle> articles = new ArrayList<>();
        articles.add(DomesticArticle.builder().articleId(3L).build());
        articles.add(DomesticArticle.builder().articleId(MAX_ARTICLE_ID).build());
        articles.add(DomesticArticle.builder().articleId(1L).build());
        articles.add(DomesticArticle.builder().articleId(3L).build());

        // when
        histrory.update(articles);

        // then
        Assertions.assertThat(histrory.getArticleId())
                  .isEqualTo(NOT_UPDATED_ARTICLE_ID);
    }

    @Test
    void update_when_given_single_article_with_matched_category() {
        // given
        final DomesticCategory category = DomesticCategory.COMPUTER;
        DomesticMulticastHistory histrory = DomesticMulticastHistory.ofNull(category);
        DomesticArticle article = DomesticArticle.builder().articleId(5L).category(category).build();

        // when
        histrory.update(article);

        // then
        Assertions.assertThat(histrory.getArticleId())
                  .isEqualTo(article.getArticleId());
    }

    @Test
    void update_when_given_articles_with_matched_category() {
        // given
        final DomesticCategory category = DomesticCategory.COMPUTER;
        final Long MAX_ARTICLE_ID = 10L;
        DomesticMulticastHistory histrory = DomesticMulticastHistory.ofNull(category);
        List<DomesticArticle> articles = new ArrayList<>();
        articles.add(DomesticArticle.builder().articleId(3L).category(category).build());
        articles.add(DomesticArticle.builder().articleId(MAX_ARTICLE_ID).category(category).build());
        articles.add(DomesticArticle.builder().articleId(1L).category(category).build());
        articles.add(DomesticArticle.builder().articleId(3L).category(category).build());

        // when
        histrory.update(articles);

        // then
        Assertions.assertThat(histrory.getArticleId())
                  .isEqualTo(MAX_ARTICLE_ID);
    }
}