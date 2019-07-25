package com.engineer.myoa.watchtower.watchtower.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramMessage {

	@JsonProperty(value = "chat_id")
	String chatId;
	String text;
}
