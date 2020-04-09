package com.engineering.myoa.watchtower.support.sensitive;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

/**
 * SensitiveProperties
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Component
@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "sensitive.token")
public class SensitiveTokenProperties {

    private List<SensitiveTokenProperty> tokens = new ArrayList<>();

    @Data
    public static class SensitiveTokenProperty {
        private String tokenName;
        private String tokenValue;
    }

    public SensitiveTokenProperty findByTokenName(@NonNull String tokenName) {
        return tokens.stream()
                     .filter(x -> x.getTokenName().equals(tokenName))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException(
                             "Not found SensitiveTokenProperty : " + tokenName));
    }

}
