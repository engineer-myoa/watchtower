package com.engineering.myoa.watchtower.support.util;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * ObjectMapperFactory
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
public final class ObjectMapperFactory {
    public ObjectMapperFactory() { }

    public static ObjectMapper getDefaultMapper() {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        // JSR 310 specifications
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModules(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

    public static ObjectMapper getHttpMapper() {
        final ObjectMapper mapper = getDefaultMapper();
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
        return mapper;
    }

}
