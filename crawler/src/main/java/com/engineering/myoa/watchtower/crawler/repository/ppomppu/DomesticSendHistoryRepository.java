package com.engineering.myoa.watchtower.crawler.repository.ppomppu;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticMulticastHistory;

/**
 * SendHistoryRepository
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Repository
public interface DomesticSendHistoryRepository extends JpaRepository<DomesticMulticastHistory, Long>,
                                                       JpaSpecificationExecutor<DomesticMulticastHistory> {

    List<DomesticMulticastHistory> findByMemberId(String memberId);

    Optional<DomesticMulticastHistory> findByMemberIdAndCategory(String memberId, DomesticCategory category);
}
