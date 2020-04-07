package com.engineering.myoa.watchtower.crawler.repository.ppomppu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaArticle;

/**
 * OverseaArticleRepository
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Repository
public interface OverseaArticleRepository extends JpaRepository<OverseaArticle, Long>,
                                                  JpaSpecificationExecutor<OverseaArticle> {
}
