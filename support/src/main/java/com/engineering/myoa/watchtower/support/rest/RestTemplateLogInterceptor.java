package com.engineering.myoa.watchtower.support.rest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
            log.info("\n[REQUEST] <<<-----------------------------------------------"
                     + "\nuri : {}, method : {}"
                     + "\nbody : {}",
                     request.getURI(),
                     request.getMethod(),
                     new String(body, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("[printRequest] error ", e);
        }
    }

    private void printResponse(BufferingClientHttpResponseWrapper responseWrapper) {
        try {
            HttpStatus statusCode = responseWrapper.getStatusCode();
            MediaType contentType = responseWrapper.getHeaders().getContentType();
            log.info("\n[RESPONSE] <<<-----------------------------------------------"
                     + "\nstatusCode : {}"
                     + "\ncontentType : {}"
                     + "\n----------------------------------------------->>>",
                     statusCode,
                     contentType);

            String response = StreamUtils.copyToString(responseWrapper.getBody(), StandardCharsets.UTF_8);

            if (isTextTypeResponse(contentType)) {
                // do nothing
                response = "[CAUTION] text type is TL;DW";
            } else if (!StringUtils.isEmpty(response)) {
                response = mapper.writeValueAsString(
                        mapper.readValue(response, Object.class));
            }
            log.info("\nbody : {}"
                     + "\n----------------------------------------------->>>", response);
        } catch (Exception e) {
            log.error("[printResponse] error ", e);
        }
    }

    private boolean isTextTypeResponse(MediaType mediaType) {
        return mediaType.getType().equals("text");
    }
}
