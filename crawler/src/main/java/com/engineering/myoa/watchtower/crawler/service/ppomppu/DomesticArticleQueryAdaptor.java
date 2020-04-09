package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticArticle;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.repository.ppomppu.DomesticArticleRepository;

/**
 * DomesticArticleService
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Service
public class DomesticArticleQueryAdaptor {

    private final DomesticArticleRepository domesticArticleRepository;

    public DomesticArticleQueryAdaptor(
            DomesticArticleRepository domesticArticleRepository) {
        this.domesticArticleRepository = domesticArticleRepository;
    }

    public List<DomesticArticle> save(List<DomesticArticle> articles) {
        return domesticArticleRepository.saveAll(articles);
    }

    public DomesticArticle save(DomesticArticle article) {
        return domesticArticleRepository.save(article);
    }

    public Optional<DomesticArticle> getLatestArticleByCategory(DomesticCategory category) {
        return domesticArticleRepository.findTopByCategoryOrderByArticleIdDesc(category);
    }

    public List<DomesticArticle> getArticles(DomesticCategory category, Long greaterThanArticleId) {
        return domesticArticleRepository
                .findByCategoryAndArticleIdGreaterThanOrderByIdAsc(category, greaterThanArticleId);
    }
}
