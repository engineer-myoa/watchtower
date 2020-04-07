package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticSendHistory;
import com.engineering.myoa.watchtower.crawler.repository.ppomppu.DomesticSendHistoryRepository;

/**
 * SendHistoryService
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Service
public class DomesticSendHistoryService {

    private final DomesticSendHistoryRepository domesticSendHistoryRepository;

    public DomesticSendHistoryService(
            DomesticSendHistoryRepository domesticSendHistoryRepository) {
        this.domesticSendHistoryRepository = domesticSendHistoryRepository;
    }

    public DomesticSendHistory save(DomesticSendHistory history) {
        return domesticSendHistoryRepository.save(history);
    }

    public List<DomesticSendHistory> findByMember(String memberId) {
        return domesticSendHistoryRepository.findByMemberId(memberId);
    }

    public Optional<DomesticSendHistory> findByMemberAndCategory(String memberId, DomesticCategory category) {
        return domesticSendHistoryRepository.findByMemberIdAndCategory(memberId, category);
    }
}
