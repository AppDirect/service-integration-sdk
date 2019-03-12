package com.appdirect.sdk.vendorFields.fieldsValidation;

import java.util.Map;

import com.appdirect.sdk.vendorFields.fieldsValidation.model.VendorFieldsValidationResponse;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;

/**
 * This is the interface you need to implement to apply vendor required requiredFields on a product
 */
@FunctionalInterface
public interface VendorFieldValidationHandler {
	VendorFieldsValidationResponse validateFields(String sku, FlowType flowType, OperationType operationType, Map<String, String> fieldValues);
}
