package com.appdirect.sdk.vendorFields.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appdirect.sdk.vendorFields.controller.VendorFieldValidationController;
import com.appdirect.sdk.vendorFields.controller.VendorRequiredFieldController;
import com.appdirect.sdk.vendorFields.handler.VendorFieldValidationHandler;
import com.appdirect.sdk.vendorFields.handler.VendorFieldValidationHandlerV2;
import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandler;
import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandlerV2;

@Configuration
public class VendorFieldsConfiguration {

    @Bean
    public VendorRequiredFieldController vendorRequiredFieldController(
            final VendorRequiredFieldHandler vendorRequiredFieldHandler,
            final VendorRequiredFieldHandlerV2 vendorRequiredFieldHandlerV2
    ) {
        return new VendorRequiredFieldController(vendorRequiredFieldHandler, vendorRequiredFieldHandlerV2);
    }

    @Bean
    public VendorRequiredFieldHandler vendorRequiredFieldHandler() {
        return (editionCode, flowType, operationType, locales) -> {
            throw new UnsupportedOperationException(String.format(
                    "Vendor Required Field Service for editionCode=%s, flowType=%s, operationType=%s and locales=%s is not supported.",
                    editionCode,
                    flowType,
                    operationType,
                    locales));
        };
    }

    @Bean
    public VendorRequiredFieldHandlerV2 vendorRequiredFieldHandlerV2() {
        return (applicationId,
                editionId,
                flowType,
                operationType,
                userId,
                companyId,
                salesAgentUserId,
                salesAgentCompanyId,
                locales,
                partnerCode) -> {
            throw new UnsupportedOperationException(String.format(
                    "Vendor Required Field Service for " +
                            "applicationId=%s," +
                            "editionId=%s," +
                            "flowType=%s," +
                            "operationType=%s," +
                            "userId=%s," +
                            "companyId=%s," +
                            "salesAgentUserId=%s," +
                            "salesAgentCompanyId=%s," +
                            "locales=%s" +
                            "partnerCode=%s" +
                            " and is not supported.",
                    applicationId,
                    editionId,
                    flowType,
                    operationType,
                    userId,
                    companyId,
                    salesAgentUserId,
                    salesAgentCompanyId,
                    locales,
                    partnerCode
            ));
        };
    }

    @Bean
    public VendorFieldValidationController vendorFieldValidationController(
            final VendorFieldValidationHandler vendorFieldValidationHandler,
            final VendorFieldValidationHandlerV2 vendorFieldValidationHandlerV2
    ) {
        return new VendorFieldValidationController(vendorFieldValidationHandler, vendorFieldValidationHandlerV2);
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

    @Bean
    public VendorFieldValidationHandlerV2 vendorFieldValidationHandlerV2() {
        return (vendorFieldsValidationRequest) -> {
            throw new UnsupportedOperationException(String.format(
                    "Vendor Fields Validation Service for editionCode=%s, flowType=%s, operationType=%s, locales=%s, and partnerCode=%s is not supported.",
                    vendorFieldsValidationRequest.getEditionCode(),
                    vendorFieldsValidationRequest.getFlowType(),
                    vendorFieldsValidationRequest.getOperationType(),
                    vendorFieldsValidationRequest.getLocales(),
                    vendorFieldsValidationRequest.getPartnerCode()));
        };
    }
}
