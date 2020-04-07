package com.engineering.myoa.watchtower.support.configuration;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.FactoryBean;

/**
 * CloseableHttpClientFactory
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
public class CloseableHttpClientFactory implements FactoryBean<CloseableHttpClient> {

    private static final int DEFAULT_MAX_PER_ROUTE = 100;
    private static final int DEFAULT_MAX_TOTAL = 500;

    public CloseableHttpClient of() {
        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL);

        return HttpClientBuilder.create()
                                .setConnectionManager(connectionManager)
                                .build();
    }

    @Override
    public CloseableHttpClient getObject() {
        return this.of();
    }

    @Override
    public Class<?> getObjectType() {
        return CloseableHttpClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
