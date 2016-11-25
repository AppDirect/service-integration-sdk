package com.appdirect.sdk.appmarket.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
class NoticeInfo {
	private NoticeType type;
	private String message;
}
