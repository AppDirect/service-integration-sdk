package com.appdirect.sdk.meteredUsage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	private Integer status = null;
	private String code = null;
	private String message = null;
}

