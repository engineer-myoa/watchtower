package com.engineering.myoa.watchtower.crawler.repository.ppomppu;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaArticle;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaCategory;

/**
 * OverseaArticleRepository
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Repository
public interface OverseaArticleRepository extends JpaRepository<OverseaArticle, Long>,
                                                  JpaSpecificationExecutor<OverseaArticle> {
    Optional<OverseaArticle> findTopByCategoryOrderByArticleIdDesc(OverseaCategory category);

    List<OverseaArticle> findByCategoryAndArticleIdGreaterThanOrderByIdAsc(OverseaCategory category,
                                                                           Long articleId);
}
