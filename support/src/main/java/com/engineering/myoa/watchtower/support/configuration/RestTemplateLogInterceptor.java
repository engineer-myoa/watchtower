package com.engineering.myoa.watchtower.support.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import com.engineering.myoa.watchtower.support.util.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * RestTemplateLogInterceptor
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Slf4j
public class RestTemplateLogInterceptor implements ClientHttpRequestInterceptor {
    private final ObjectMapper mapper = ObjectMapperFactory.getHttpMapper();

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        try {
            printRequest(request, body);

            final ClientHttpResponse response = execution.execute(request, body);
            final BufferingClientHttpResponseWrapper bufferedResponseWrapper =
                    new BufferingClientHttpResponseWrapper(response);

            printResponse(bufferedResponseWrapper);

            return bufferedResponseWrapper;
        } catch (IOException e) {
            log.error("[intercept] error", e);
            throw e;
        }
    }

    private void printRequest(HttpRequest request, byte[] body) {
        try {
            log.info("[REQUEST] uri : {} method : {}  body : {}",
                     request.getURI(),
                     request.getMethod(),
                     new String(body, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("[printRequest] error ", e);
        }
    }

    private void printResponse(BufferingClientHttpResponseWrapper responseWrapper) {
        try {
            log.info("[RESPONSE]-----------------------------------------------"
                     + "\nstatusCode : {}"
                     + "\nstatusText : {}"
                     + "\ncontentType : {}",
                     responseWrapper.getStatusCode(),
                     responseWrapper.getStatusText(),
                     responseWrapper.getHeaders().getContentType());

            String response = StreamUtils.copyToString(responseWrapper.getBody(), StandardCharsets.UTF_8);
            if (!StringUtils.isEmpty(response)) {
                response = mapper.writeValueAsString(
                        mapper.readValue(response, Object.class));
            }
            log.info("body : {}"
                     + "\n-----------------------------------------------", response);
        } catch (Exception e) {
            log.error("[printResponse] error ", e);
        }
    }
}
