package com.appdirect.sdk.vendorFields.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.vendorFields.controller.VendorFieldValidationController;
import com.appdirect.sdk.vendorFields.controller.VendorRequiredFieldController;
import com.appdirect.sdk.vendorFields.handler.VendorFieldValidationHandler;
import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandler;

@Configuration
public class VendorFieldsConfiguration {
    
    @Bean
    public VendorRequiredFieldController vendorRequiredFieldController(VendorRequiredFieldHandler vendorRequiredFieldHandler) {
        return new VendorRequiredFieldController(vendorRequiredFieldHandler);
    }

    @Bean
    public VendorRequiredFieldHandler vendorRequiredFieldHandler() {
        return (editionCode, flowType, operationType, locales) -> {
            throw new UnsupportedOperationException(String.format(
                    "Vendor Required Field Service for editionCode=%s, flow type=%s, operation type=%s and locales=%s is not supported.",
                    editionCode,
                    flowType,
                    operationType,
                    locales));
        };
    }

    @Bean
    public VendorFieldValidationController vendorFieldValidationController(VendorFieldValidationHandler vendorFieldValidationHandler) {
        return new VendorFieldValidationController(vendorFieldValidationHandler);
    }

    @Bean
    public VendorFieldValidationHandler vendorFieldValidationHandler() {
        return (vendorFieldsValidationRequest) -> {
            throw new UnsupportedOperationException(String.format(
                    "Vendor Fields Validation Service for editionCode=%s, flowType=%s, operationType=%s and locales=%s is not supported.",
                    vendorFieldsValidationRequest.getEditionCode(),
                    vendorFieldsValidationRequest.getFlowType(),
                    vendorFieldsValidationRequest.getOperationType(),
                    vendorFieldsValidationRequest.getLocales()));
        };
    }
}
