package com.engineering.myoa.watchtower.support.dto.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ResultCode
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS("200", "Success"),
    BAD_REQUEST("400", "Bad Request"),
    NOT_FOUND("404", "Not Found"),
    UNPROCESSABLE_ENTITY("422", "Unprocessable Entity"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    SERVICE_UNAVAILABLE("503", "Service Unavailable"),
    ;

    private final String code;
    private final String message;
}
