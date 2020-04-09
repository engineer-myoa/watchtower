package com.engineering.myoa.watchtower.crawler.service.ppomppu;

import java.util.List;

import org.springframework.stereotype.Component;

import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.OverseaCategory;
import com.engineering.myoa.watchtower.crawler.doamin.ppomppu.SubscribedOverseaCategory;
import com.engineering.myoa.watchtower.crawler.repository.ppomppu.SubscribedOverseaCategoryRepository;

/**
 * SubscribedOverseaCategoryQueryAdaptor
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-09
 *
 */
@Component
public class SubscribedOverseaCategoryQueryAdaptor {

    private final SubscribedOverseaCategoryRepository subscribedOverseaCategoryRepository;

    public SubscribedOverseaCategoryQueryAdaptor(
            SubscribedOverseaCategoryRepository subscribedDomesticCategoryRepository) {
        this.subscribedOverseaCategoryRepository = subscribedDomesticCategoryRepository;
    }

    public List<SubscribedOverseaCategory> findByMemberId(String memberId) {
        return subscribedOverseaCategoryRepository.findByMemberId(memberId);
    }

    public List<SubscribedOverseaCategory> findByCategory(OverseaCategory category) {
        return subscribedOverseaCategoryRepository.findByCategory(category);
    }

}
