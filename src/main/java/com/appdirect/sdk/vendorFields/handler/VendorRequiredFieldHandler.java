package com.appdirect.sdk.vendorFields.handler;

import java.util.List;
import java.util.Locale;

import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponse;

/**
 * This is the interface you need to implement to apply vendor required requiredFields on a product
 */
@FunctionalInterface
public interface VendorRequiredFieldHandler {
	VendorRequiredFieldsResponse getRequiredFields(String editionCode, FlowType flowType, OperationType operationType, List<Locale> locales);
}
