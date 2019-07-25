package com.engineer.myoa.watchtower.property;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListResult<T> {
	List<T> result;
	Long total;
	Long offset;
	Long limit;

	public static <T> ListResult<T> of(List<T> result) {
		return ListResult.<T>builder()
			.result(result)
			.build();
	}
}
