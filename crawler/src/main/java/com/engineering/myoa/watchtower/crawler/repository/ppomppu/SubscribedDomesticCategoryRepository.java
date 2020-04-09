package com.engineering.myoa.watchtower.crawler.repository.ppomppu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.SubscribedDomesticCategory;

/**
 * SubscribedDomesticCategoryRepository
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Repository
public interface SubscribedDomesticCategoryRepository extends JpaRepository<SubscribedDomesticCategory, Long> {

    List<SubscribedDomesticCategory> findByMemberId(String memberId);

    List<SubscribedDomesticCategory> findByCategory(DomesticCategory category);
}
