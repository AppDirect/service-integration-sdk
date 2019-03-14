package com.appdirect.sdk.vendorFields.handler;

import java.util.Map;

import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationResponse;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;

/**
 * This is the interface you need to implement to have VendorFieldValidation on a product
 */
@FunctionalInterface
public interface VendorFieldValidationHandler {
	VendorFieldsValidationResponse validateFields(String sku, FlowType flowType, OperationType operationType, Map<String, String> fieldValues);
}
