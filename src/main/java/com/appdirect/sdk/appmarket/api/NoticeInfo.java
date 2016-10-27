package com.appdirect.sdk.appmarket.api;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class NoticeInfo {
	private NoticeType type;
	private String message;
}
