package com.engineering.myoa.watchtower.crawler.properties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.engineering.myoa.watchtower.crawler.configuration.EhCacheNames;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * ApisProperties
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Slf4j
@Component
@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private List<ApiProperty> apis = new ArrayList<>();

    @Data
    public static class ApiProperty implements Serializable {
        private String apiName;
        private String apiPath;
    }

    @Cacheable(cacheManager = "ehCacheManager", value = { EhCacheNames.CRAWLER_META }, key = "#apiName")
    public ApiProperty findByApiName(String apiName) {
        return apis.stream()
                   .filter(e -> e.getApiName().equals(apiName))
                   .findFirst()
                   .orElseThrow(() -> new IllegalArgumentException("Not found ApiProperty : " + apiName));
    }

}
