package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.List;

import org.springframework.stereotype.Component;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.DomesticCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.SubscribedDomesticCategory;
import com.engineering.myoa.watchtower.crawler.repository.ppomppu.SubscribedDomesticCategoryRepository;

/**
 * SubscribedDomesticCategoryQueryAdaptor
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Component
public class SubscribedDomesticCategoryQueryAdaptor {

    private final SubscribedDomesticCategoryRepository subscribedDomesticCategoryRepository;

    public SubscribedDomesticCategoryQueryAdaptor(
            SubscribedDomesticCategoryRepository subscribedDomesticCategoryRepository) {
        this.subscribedDomesticCategoryRepository = subscribedDomesticCategoryRepository;
    }

    public List<SubscribedDomesticCategory> findByMemberId(String memberId) {
        return subscribedDomesticCategoryRepository.findByMemberId(memberId);
    }

    public List<SubscribedDomesticCategory> findByCategory(DomesticCategory category) {
        return subscribedDomesticCategoryRepository.findByCategory(category);
    }

}
