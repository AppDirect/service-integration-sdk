package com.appdirect.sdk.appmarket.validation;

import java.util.Set;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.appdirect.sdk.appmarket.events.OrderValidationStatus;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ValidationResponse {
	Set<OrderValidationStatus> result;
}
