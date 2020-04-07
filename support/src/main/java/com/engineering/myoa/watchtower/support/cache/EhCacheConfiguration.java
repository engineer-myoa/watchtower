package com.engineering.myoa.watchtower.support.cache;

import java.io.IOException;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * EhCacheConfiguration
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@EnableCaching
@Configuration
public class EhCacheConfiguration {

    @Bean("ehCacheManager")
    public JCacheCacheManager ehCacheManager(JCacheManagerFactoryBean ehCacheManagerFactoryBean)
            throws IOException {
        final JCacheCacheManager ehCacheManager = new JCacheCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheManager;
    }

    @Bean
    public JCacheManagerFactoryBean ehCacheManagerFactoryBean() throws IOException {
        final JCacheManagerFactoryBean factoryBean = new JCacheManagerFactoryBean();
        factoryBean.setCacheManagerUri(new ClassPathResource("ehcache/default.xml").getURI());
        return factoryBean;
    }
//        return new EhcacheManager(ehCacheDefaultCache().getObject());
//    }
//
//    @Bean
//    public EhCacheManagerFactoryBean ehCacheDefaultCache() {
//        final EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
//        factoryBean.setConfigLocation(new ClassPathResource("ehcache/default.xml"));
//        factoryBean.setShared(true);
//        return factoryBean;
//    }

}
