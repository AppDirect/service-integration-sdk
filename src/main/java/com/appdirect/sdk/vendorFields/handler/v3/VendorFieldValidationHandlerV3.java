package com.appdirect.sdk.vendorFields.handler.v3;

import com.appdirect.sdk.vendorFields.model.v3.VendorFieldsValidationRequestV3;
import com.appdirect.sdk.vendorFields.model.v3.VendorFieldsValidationResponseV3;

/**
 * This is the interface you need to implement to have VendorFieldValidation on a product
 */
@FunctionalInterface
public interface VendorFieldValidationHandlerV3 {
    VendorFieldsValidationResponseV3 validateFields(VendorFieldsValidationRequestV3 vendorFieldsValidationRequest);
}
