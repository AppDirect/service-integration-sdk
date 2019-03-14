package com.appdirect.sdk.vendorFields.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VendorFieldsValidationRequest {
	String sku;
	FlowType flowType;
	OperationType operationType;
	Map<String, String> fieldValues;
}
