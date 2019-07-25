package com.engineer.myoa.watchtower.property;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> {

	HttpStatus httpStatus;
	String message;
	int resultCode;
	T result;

	public static <T> CommonResponse<T> success(T result) {
		return success(result, HttpStatus.OK);
	}

	public static <T> CommonResponse<T> success(T result, HttpStatus httpStatus) {
		return CommonResponse.<T>builder()
			.httpStatus(httpStatus)
			.result(result)
			.build();
	}

	public static <T> CommonResponse<T> fail(String message, HttpStatus httpStatus) {
		return CommonResponse.<T>builder()
			.httpStatus(httpStatus)
			.message(message)
			.build();
	}
}
