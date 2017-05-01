package com.appdirect.sdk.appmarket.events;

import java.util.Set;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ValidationResponse {
	Set<OrderValidationStatus> result;
}
