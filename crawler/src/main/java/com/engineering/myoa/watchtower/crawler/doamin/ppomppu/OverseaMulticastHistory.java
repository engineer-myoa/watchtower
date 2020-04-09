package com.engineering.myoa.watchtower.crawler.doamin.ppomppu;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * SendHistory
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Getter
@Entity
@Table(name = "ppomppu_oversea_multicast_history")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OverseaMulticastHistory {
    private static final Logger logger = LoggerFactory.getLogger(OverseaMulticastHistory.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    Long articleId;

    @Column
    @Enumerated(EnumType.STRING)
    private OverseaCategory category;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public void update(OverseaArticle article) {
        if (isDifferentCategory(article)) {
            return;
        }

        this.articleId = article.getArticleId();
    }

    public void update(List<OverseaArticle> articles) {

        if (articles.stream().anyMatch(this::isDifferentCategory)) {
            logger.info("[OverseaMulticastHistory.update] Different category. skipped to: {}", this.articleId);
            return;
        }

        OverseaArticle latestArticle = articles.stream()
                                               .max((o1, o2) -> o1.getArticleId().compareTo(o2.getArticleId()))
                                               .orElseGet(OverseaArticle::ofNull);

        logger.info("[OverseaMulticastHistory.update] category: {}, latest articleId: {}",
                    latestArticle.getCategory().getDescription(), latestArticle.getArticleId());
        this.articleId = latestArticle.getArticleId();
    }

    private boolean isDifferentCategory(OverseaArticle article) {
        return !article.isSameCategory(this.category);
    }

    public static OverseaMulticastHistory ofNull(OverseaCategory category) {
        return OverseaMulticastHistory.builder()
                                      .articleId(0L)
                                      .category(category)
                                      .build();
    }

    @Override
    public String toString() {
        return "OverseaMulticastHistory{" +
               "id=" + id +
               ", articleId=" + articleId +
               ", category=" + category +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               '}';
    }
}
