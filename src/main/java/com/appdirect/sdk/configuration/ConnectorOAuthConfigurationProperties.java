package com.appdirect.sdk.configuration;

import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

@Getter
@Setter
@ConfigurationProperties(prefix = "connector.oauth")
class ConnectorOAuthConfigurationProperties {
	@Value("${connector.allowed.credentials:#{null}}")
	private Optional<String> legacyCredentials; //Kept for backward compatibility

	private Map<String, String> credentials = Maps.newHashMap();
}
