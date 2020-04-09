package com.engineering.myoa.watchtower.crawler.repository.ppomppu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.SubscribedOverseaCategory;

/**
 * SubscribedOverseaCategoryRepository
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Repository
public interface SubscribedOverseaCategoryRepository extends JpaRepository<SubscribedOverseaCategory, Long> {

    List<SubscribedOverseaCategory> findByMemberId(String memberId);

    List<SubscribedOverseaCategory> findByCategory(OverseaCategory category);
}
