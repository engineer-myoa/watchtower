package com.engineering.myoa.watchtower.support.dto;

import org.springframework.lang.Nullable;

import com.engineering.myoa.watchtower.support.dto.code.ResultCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ApiResponseContainer
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseContainer<T> {

    private String resultCode;
    private boolean success;
    private String errMsg;
    private T result;

    public static ApiResponseContainer<Object> fail(@Nullable String resultCode, @Nullable String errMsg) {
        return builder()
                .resultCode(resultCode)
                .success(false)
                .errMsg(errMsg)
                .build();
    }

    public static <T> ApiResponseContainer<T> of(T response) {
        return ApiResponseContainer.<T>builder()
                .success(true)
                .resultCode(ResultCode.SUCCESS.getCode())
                .result(response)
                .build();
    }

}
