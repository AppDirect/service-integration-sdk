package com.appdirect.sdk.vendorFields.model;

/**
 * This is the interface you need to implement to apply vendor required requiredFields on a product
 */
@FunctionalInterface
public interface VendorRequiredFieldHandler {
	VendorRequiredFieldsResponse getRequiredFields(String sku, FlowType flowType, OperationType operationType);
}
