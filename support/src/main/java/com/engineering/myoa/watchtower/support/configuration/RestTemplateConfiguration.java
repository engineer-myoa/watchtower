package com.engineering.myoa.watchtower.support.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.engineering.myoa.watchtower.support.util.ObjectMapperFactory;

/**
 * RestTemplateConfiguration
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Configuration
@ComponentScan(basePackageClasses = RestTemplateBase.class)
@ConditionalOnProperty(name = "resttemplate.init", havingValue = "true")
public class RestTemplateConfiguration {
    private static final int DEFAULT_READ_TIMEOUT = 5_000;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 5_000;

    @Primary
    @Bean
    public RestTemplate verboseRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(httpRequestFactory());
        restTemplate.getMessageConverters().add(messageConverter());
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateLogInterceptor()));
        return restTemplate;
    }

    @Bean
    public RestTemplate silentRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(httpRequestFactory());
        return restTemplate;
    }

    private HttpComponentsClientHttpRequestFactory httpRequestFactory() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(closeableHttpClient());
        httpRequestFactory.setReadTimeout(DEFAULT_READ_TIMEOUT);
        httpRequestFactory.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
        return httpRequestFactory;
    }

    @Primary
    @Bean
    public CloseableHttpClient closeableHttpClient() {
        return closeableHttpClientFactory().getObject();
    }

    @Bean
    public CloseableHttpClientFactory closeableHttpClientFactory() {
        final CloseableHttpClientFactory closeableHttpClientFactory = new CloseableHttpClientFactory();
        return closeableHttpClientFactory;
    }

    private MappingJackson2HttpMessageConverter messageConverter() {
        final MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(ObjectMapperFactory.getHttpMapper());
        messageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
                                                              MediaType.APPLICATION_XML,
                                                              MediaType.APPLICATION_FORM_URLENCODED));
        return messageConverter;
    }

}
