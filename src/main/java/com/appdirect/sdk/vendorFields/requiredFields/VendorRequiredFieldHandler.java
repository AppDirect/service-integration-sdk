package com.appdirect.sdk.vendorFields.requiredFields;

import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.requiredFields.model.VendorRequiredFieldsResponse;

/**
 * This is the interface you need to implement to apply vendor required requiredFields on a product
 */
@FunctionalInterface
public interface VendorRequiredFieldHandler {
	VendorRequiredFieldsResponse getRequiredFields(String sku, FlowType flowType, OperationType operationType);
}
