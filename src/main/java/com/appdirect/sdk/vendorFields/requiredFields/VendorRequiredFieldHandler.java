package com.appdirect.sdk.vendorFields.fields;

import com.appdirect.sdk.vendorFields.fields.model.FlowType;
import com.appdirect.sdk.vendorFields.fields.model.OperationType;
import com.appdirect.sdk.vendorFields.fields.model.VendorRequiredFieldsResponse;

/**
 * This is the interface you need to implement to apply vendor required fields on a product
 */
@FunctionalInterface
public interface VendorRequiredFieldHandler {
	VendorRequiredFieldsResponse getRequiredFields(String sku, FlowType flowType, OperationType operationType);
}
