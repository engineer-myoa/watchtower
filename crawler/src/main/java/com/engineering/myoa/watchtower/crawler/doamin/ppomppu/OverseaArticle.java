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

}
