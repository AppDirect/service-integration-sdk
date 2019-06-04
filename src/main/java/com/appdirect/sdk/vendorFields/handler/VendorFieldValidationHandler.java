package com.appdirect.sdk.vendorFields.handler;

import java.util.List;
import java.util.Locale;

import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationRequest;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationResponse;

/**
 * This is the interface you need to implement to have VendorFieldValidation on a product
 */
@FunctionalInterface
public interface VendorFieldValidationHandler {
	VendorFieldsValidationResponse validateFields(VendorFieldsValidationRequest vendorFieldsValidationRequest);
}
