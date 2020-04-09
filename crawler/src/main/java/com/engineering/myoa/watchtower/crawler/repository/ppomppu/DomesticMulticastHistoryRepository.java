package com.engineering.myoa.watchtower.crawler.repository.ppomppu;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticMulticastHistory;

/**
 * DomesticMulticastHistoryRepository
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Repository
public interface DomesticMulticastHistoryRepository extends JpaRepository<DomesticMulticastHistory, Long>,
                                                            JpaSpecificationExecutor<DomesticMulticastHistory> {
    List<DomesticMulticastHistory> findByCategory(DomesticCategory category);

    Optional<DomesticMulticastHistory> findTopByCategoryOrderByIdDesc(DomesticCategory category);
}
