package com.engineering.myoa.watchtower.crawler.repository.ppomppu;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaMulticastHistory;

/**
 * OverseaMulticastHistoryRepository
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-09
 *
 */
@Repository
public interface OverseaMulticastHistoryRepository extends JpaRepository<OverseaMulticastHistory, Long>,
                                                           JpaSpecificationExecutor<OverseaMulticastHistory> {
    List<OverseaMulticastHistory> findByCategory(OverseaCategory category);

    Optional<OverseaMulticastHistory> findTopByCategoryOrderByIdDesc(OverseaCategory category);
}
