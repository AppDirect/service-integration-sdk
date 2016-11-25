package com.appdirect.sdk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BillingAPIResult {
	private boolean success;
	private String message;
}
