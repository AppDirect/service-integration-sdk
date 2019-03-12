package com.appdirect.sdk.vendorFields.validation;

import java.util.Map;

import com.appdirect.sdk.vendorFields.validation.model.FlowType;
import com.appdirect.sdk.vendorFields.validation.model.OperationType;
import com.appdirect.sdk.vendorFields.validation.model.VendorFieldsValidationResponse;

/**
 * This is the interface you need to implement to apply vendor required fields on a product
 */
@FunctionalInterface
public interface VendorFieldValidationHandler {
	VendorFieldsValidationResponse validateFields(String sku, FlowType flowType, OperationType operationType, Map<String, String> fieldValues);
}
