package com.appdirect.sdk.appmarket.events;

import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EventWithContextWithConfiguration extends EventWithContext {
	private Map<String, String> configuration = new HashMap<>();

	public EventWithContextWithConfiguration(
		String consumerKeyUsedByTheRequest,
		Map<String, String []> queryParameters,
		EventFlag flag,
		String eventToken,
		String marketplaceUrl,
		Map<String, String> configuration) {
		super(consumerKeyUsedByTheRequest, queryParameters, flag, eventToken, marketplaceUrl);
		this.configuration = configuration;
	}

	/**
	 * Returns the configuration that was passed to the endpoint when this event was received.
	 *
	 * @return an unmodifiable view of the configuration map.
	 */
	public Map<String, String> getConfiguration() {
		return new HashMap<>(isNull(configuration) ? new HashMap<>() : configuration);
	}
}
