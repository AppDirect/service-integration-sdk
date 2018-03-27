package com.appdirect.sdk.web.oauth.model;

import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "openid.secured.url")
public class OpenIdCustomUrlPattern {
	private List<String> patterns;
}
