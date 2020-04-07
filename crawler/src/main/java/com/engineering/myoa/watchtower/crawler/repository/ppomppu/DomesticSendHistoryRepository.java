package com.engineering.myoa.watchtower.crawler.repository.ppomppu;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticSendHistory;

/**
 * SendHistoryRepository
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Repository
public interface DomesticSendHistoryRepository extends JpaRepository<DomesticSendHistory, Long>,
                                                       JpaSpecificationExecutor<DomesticSendHistory> {

    List<DomesticSendHistory> findByMemberId(String memberId);

    Optional<DomesticSendHistory> findByMemberIdAndCategory(String memberId, DomesticCategory category);
}
