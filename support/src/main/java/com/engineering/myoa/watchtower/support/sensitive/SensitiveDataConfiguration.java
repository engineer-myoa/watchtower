package com.engineering.myoa.watchtower.support.sensitive;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * SensitiveConfiguration
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Configuration
@ConditionalOnProperty(name = "sensitive.init", havingValue = "true")
@ComponentScan(basePackageClasses = SensitiveData.class)
public class SensitiveDataConfiguration {
}
