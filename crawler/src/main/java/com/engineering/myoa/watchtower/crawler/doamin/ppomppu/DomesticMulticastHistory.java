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
 * DomesticSendHistory
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Getter
@Entity
@Table(name = "ppomppu_domestic_multicast_history")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DomesticMulticastHistory {
    private static final Logger logger = LoggerFactory.getLogger(DomesticMulticastHistory.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    Long articleId;

    @Column
    @Enumerated(EnumType.STRING)
    private DomesticCategory category;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public void update(DomesticArticle article) {
        if (isDifferentCategory(article)) {
            return;
        }

        this.articleId = article.getArticleId();
    }

    public void update(List<DomesticArticle> articles) {

        if (articles.stream().anyMatch(this::isDifferentCategory)) {
            logger.info("[DomesticMulticastHistory.update] Different category. skipped to: {}", this.articleId);
            return;
        }

        DomesticArticle latestArticle = articles.stream()
                                                .max((o1, o2) -> o1.getArticleId().compareTo(o2.getArticleId()))
                                                .orElseGet(DomesticArticle::ofNull);

        logger.info("[DomesticMulticastHistory.update] category: {}, latest articleId: {}",
                    latestArticle.getCategory().getDescription(), latestArticle.getArticleId());
        this.articleId = latestArticle.getArticleId();
    }

    private boolean isDifferentCategory(DomesticArticle article) {
        return !article.isSameCategory(this.category);
    }

    public static DomesticMulticastHistory ofNull(DomesticCategory category) {
        return DomesticMulticastHistory.builder()
                                       .articleId(0L)
                                       .category(category)
                                       .build();
    }

    @Override
    public String toString() {
        return "DomesticMulticastHistory{" +
               "id=" + id +
               ", articleId=" + articleId +
               ", category=" + category +
               ", createdAt=" + createdAt +
               ", modifiedAt=" + modifiedAt +
               '}';
    }
}
