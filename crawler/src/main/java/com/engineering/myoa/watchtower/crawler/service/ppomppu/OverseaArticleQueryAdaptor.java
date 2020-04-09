package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaArticle;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaCategory;
import com.engineering.myoa.watchtower.crawler.repository.ppomppu.OverseaArticleRepository;

/**
 * OverseaArticleService
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Service
public class OverseaArticleQueryAdaptor {

    private final OverseaArticleRepository overseaArticleRepository;

    public OverseaArticleQueryAdaptor(
            OverseaArticleRepository overseaArticleRepository) {
        this.overseaArticleRepository = overseaArticleRepository;
    }

    public List<OverseaArticle> save(List<OverseaArticle> articles) {
        return overseaArticleRepository.saveAll(articles);
    }

    public OverseaArticle save(OverseaArticle article) {
        return overseaArticleRepository.save(article);
    }

    public Optional<OverseaArticle> getLatestArticleByCategory(OverseaCategory category) {
        return overseaArticleRepository.findTopByCategoryOrderByArticleIdDesc(category);
    }

    public List<OverseaArticle> getArticles(OverseaCategory category, Long greaterThanArticleId) {
        return overseaArticleRepository
                .findByCategoryAndArticleIdGreaterThanOrderByIdAsc(category, greaterThanArticleId);
    }
}
