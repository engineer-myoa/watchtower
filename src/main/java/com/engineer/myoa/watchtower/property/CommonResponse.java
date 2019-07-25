package com.engineer.myoa.watchtower.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> {

	String message;
	int resultCode;
	T result;

	public static <T> CommonResponse<T> success(T result) {
		return CommonResponse.<T>builder()
			.result(result)
			.build();
	}
}
