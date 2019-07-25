package com.appdirect.sdk.meteredUsage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeteredUsageRequestAccepted {
	private String requestId = null;
	private String idempotencyKey = null;
}

