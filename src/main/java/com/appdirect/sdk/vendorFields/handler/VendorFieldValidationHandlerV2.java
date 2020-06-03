package com.appdirect.sdk.vendorFields.handler;

import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationRequestV2;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationResponseV2;

/**
 * This is the interface you need to implement to have VendorFieldValidation on a product
 */
@FunctionalInterface
public interface VendorFieldValidationHandlerV2 {
    VendorFieldsValidationResponseV2 validateFields(VendorFieldsValidationRequestV2 vendorFieldsValidationRequest);
}
