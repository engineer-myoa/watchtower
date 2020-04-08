package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticMulticastHistory;
import com.engineering.myoa.watchtower.crawler.repository.ppomppu.DomesticMulticastHistoryRepository;

/**
 * DomesticMulticastQueryAdaptor
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Component
public class DomesticMulticastQueryAdaptor {

    private final DomesticMulticastHistoryRepository domesticMulticastHistoryRepository;

    public DomesticMulticastQueryAdaptor(
            DomesticMulticastHistoryRepository domesticMulticastHistoryRepository) {
        this.domesticMulticastHistoryRepository = domesticMulticastHistoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<DomesticMulticastHistory> findOneFromCategory(DomesticCategory category) {
        return domesticMulticastHistoryRepository.findTopByCategoryOrderByIdDesc(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(DomesticMulticastHistory history) {
        domesticMulticastHistoryRepository.save(history);
    }
}
