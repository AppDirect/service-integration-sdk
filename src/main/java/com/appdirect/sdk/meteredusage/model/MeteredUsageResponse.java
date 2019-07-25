package com.appdirect.sdk.meteredusage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteredUsageResponse {
	private String requestId;
	private String idempotencyKey;
}

