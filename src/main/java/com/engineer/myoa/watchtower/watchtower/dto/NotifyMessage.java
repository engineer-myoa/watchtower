package com.engineer.myoa.watchtower.watchtower.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyMessage {

	private String token;
	private String message;
	private List<String> chatIdList;

}
