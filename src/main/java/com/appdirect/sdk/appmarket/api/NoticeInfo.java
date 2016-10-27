package com.appdirect.sdk.appmarket.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeInfo implements Serializable {
	private static final long serialVersionUID = 5794025019463371366L;

	private NoticeType type;
	private String message;
}
