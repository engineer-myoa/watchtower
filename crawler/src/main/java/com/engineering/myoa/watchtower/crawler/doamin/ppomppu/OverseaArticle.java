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
@Table(name = "ppomppu_oversea_article")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverseaArticle implements PpomppuArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long articleId;

    @Column
    @Enumerated(EnumType.STRING)
    private OverseaCategory category;

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

    public boolean isSameCategory(OverseaCategory category) {
        return this.category == category;
    }

    public static OverseaArticle of(Element element, OverseaCategory category) {
        String articleId = PpomppuArticleExtractor.getArticleId(element);
        if (articleId.isEmpty()) {
            return null;
        }

        return OverseaArticle.builder()
                             .category(category)
                             .articleId(Long.parseLong(articleId))
                             .title(PpomppuArticleExtractor.getTitle(element))
                             .link(PpomppuArticleExtractor.getLink(element))
                             .thumbnailUrl(PpomppuArticleExtractor.getThumbnail(element))
                             .build();

    }

    public static OverseaArticle ofNull() {
        return OverseaArticle.builder()
                             .articleId(0L)
                             .build();
    }
}
