package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaMulticastHistory;
import com.engineering.myoa.watchtower.crawler.repository.ppomppu.OverseaMulticastHistoryRepository;

/**
 * OverseaMulticastQueryAdaptor
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Component
public class OverseaMulticastQueryAdaptor {

    private final OverseaMulticastHistoryRepository overseaMulticastHistoryRepository;

    public OverseaMulticastQueryAdaptor(
            OverseaMulticastHistoryRepository overseaMulticastHistoryRepository) {
        this.overseaMulticastHistoryRepository = overseaMulticastHistoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<OverseaMulticastHistory> findOneFromCategory(OverseaCategory category) {
        return overseaMulticastHistoryRepository.findTopByCategoryOrderByIdDesc(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(OverseaMulticastHistory history) {
        overseaMulticastHistoryRepository.save(history);
    }
}
