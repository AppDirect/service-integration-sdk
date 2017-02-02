package com.appdirect.sdk.appmarket.migration;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.databind.ObjectMapper;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class CustomerAccount {
	@Setter
	@Getter
	private String tenantId;
	@Setter
	@Getter
	private String tenantDomain;
	@Setter
	@Getter
	private String externalVendorId;
	private Map<String, Object> customData;

	public <T> Optional<T> parseCustomData(Class<T> dataClass) {
		if (customData == null) {
			return Optional.empty();
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return Optional.ofNullable(objectMapper.readValue(objectMapper.writeValueAsString(customData), dataClass));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
