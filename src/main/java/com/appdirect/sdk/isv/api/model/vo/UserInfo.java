package com.appdirect.sdk.isv.api.model.vo;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfo implements Serializable {
	private static final long serialVersionUID = -4844899619664634913L;

	private String uuid;
	private String openId;
	private String email;
	private String firstName;
	private String lastName;
	private String language;
	private String locale;
	private Map<String, String> attributes;
}
