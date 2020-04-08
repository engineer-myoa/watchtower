package com.engineering.myoa.watchtower.crawler.doamin.ppomppu;

import java.time.LocalDateTime;

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
import org.jsoup.nodes.Element;

import com.engineering.myoa.watchtower.crawler.util.PpomppuArticleExtractor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PpomppuArticle
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Getter
@Entity
@Table(name = "ppomppu_domestic_article")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomesticArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long articleId;

    @Column
    @Enumerated(EnumType.STRING)
    private DomesticCategory category;

    @Column
    private String title;

    @Column
    private String link;

    @Column
    private String thumbnailUrl;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public boolean isSameCategory(DomesticCategory category) {
        return this.category == category;
    }

    public static DomesticArticle of(Element element, DomesticCategory category) {
        String articleId = PpomppuArticleExtractor.getArticleId(element);
        if (articleId.isEmpty()) {
            return null;
        }

        return DomesticArticle.builder()
                              .category(category)
                              .articleId(Long.parseLong(articleId))
                              .title(PpomppuArticleExtractor.getTitle(element))
                              .link(PpomppuArticleExtractor.getLink(element))
                              .thumbnailUrl(PpomppuArticleExtractor.getThumbnail(element))
                              .build();

    }

    public static DomesticArticle ofNull() {
        return DomesticArticle.builder()
                              .articleId(0L)
                              .build();
    }

}
