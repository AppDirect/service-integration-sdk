package com.appdirect.sdk.vendorrequiredfields;

import com.appdirect.sdk.vendorrequiredfields.model.FlowType;
import com.appdirect.sdk.vendorrequiredfields.model.OperationType;
import com.appdirect.sdk.vendorrequiredfields.model.VendorRequiredFieldResponse;

/**
 * This is the interface you need to implement to apply vendor required fields on a product
 */
@FunctionalInterface
public interface VendorRequiredFieldHandler {
	VendorRequiredFieldResponse getRequiredFields(String sku, FlowType flowType, OperationType operationType);
}
