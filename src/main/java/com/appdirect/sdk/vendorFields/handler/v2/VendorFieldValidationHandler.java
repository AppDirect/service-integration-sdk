package com.appdirect.sdk.vendorFields.handler.v2;

import com.appdirect.sdk.vendorFields.model.v2.VendorFieldsValidationRequestV2;
import com.appdirect.sdk.vendorFields.model.v2.VendorFieldsValidationResponseV2;

/**
 * This is the interface you need to implement to have VendorFieldValidation on a product
 */
@FunctionalInterface
public interface VendorFieldValidationHandler {
    VendorFieldsValidationResponseV2 validateFields(VendorFieldsValidationRequestV2 vendorFieldsValidationRequest);
}
