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
    public VendorRequiredFieldController vendorRequiredFieldController(
            final VendorRequiredFieldHandler vendorRequiredFieldHandler,
            final com.appdirect.sdk.vendorFields.handler.v2.VendorRequiredFieldHandler vendorRequiredFieldHandlerV2
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
    public com.appdirect.sdk.vendorFields.handler.v2.VendorRequiredFieldHandler vendorRequiredFieldHandlerV2() {
        return (applicationId,
                editionId,
                flowType,
                operationType,
                userId,
                companyId,
                salesAgentUserId,
                salesAgentCompanyId,
                locale,
                partnerCode) -> {
            throw new UnsupportedOperationException(String.format(
                    "Vendor Required Field Service for " +
                            "applicationId=%s, " +
                            "editionId=%s, " +
                            "flowType=%s, " +
                            "operationType=%s, " +
                            "userId=%s, " +
                            "companyId=%s, " +
                            "salesAgentUserId=%s, " +
                            "salesAgentCompanyId=%s, " +
                            "locale=%s, " +
                            "partnerCode=%s " +
                            "is not supported.",
                    applicationId,
                    editionId,
                    flowType,
                    operationType,
                    userId,
                    companyId,
                    salesAgentUserId,
                    salesAgentCompanyId,
                    locale,
                    partnerCode
            ));
        };
    }

    @Bean
    public VendorFieldValidationController vendorFieldValidationController(
            final VendorFieldValidationHandler vendorFieldValidationHandler,
            final com.appdirect.sdk.vendorFields.handler.v2.VendorFieldValidationHandler vendorFieldValidationHandlerV2
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
    public com.appdirect.sdk.vendorFields.handler.v2.VendorFieldValidationHandler vendorFieldValidationHandlerV2() {
        return (vendorFieldsValidationRequest) -> {
            throw new UnsupportedOperationException(String.format(
                    "Vendor Fields Validation Service for " +
                            "applicationIdentifier=%s, " +
                            "editionId=%s, " +
                            "flowType=%s, " +
                            "operationType=%s, " +
                            "userId=%s, " +
                            "companyId=%s, " +
                            "salesAgentUserId=%s, " +
                            "salesAgentCompanyId=%s, " +
                            "locale=%s, " +
                            "partnerCode=%s, " +
                            "is not supported.",
                    vendorFieldsValidationRequest.getApplicationId(),
                    vendorFieldsValidationRequest.getEditionId(),
                    vendorFieldsValidationRequest.getFlowType(),
                    vendorFieldsValidationRequest.getOperationType(),
                    vendorFieldsValidationRequest.getUserId(),
                    vendorFieldsValidationRequest.getCompanyId(),
                    vendorFieldsValidationRequest.getSalesAgentUserId(),
                    vendorFieldsValidationRequest.getSalesAgentCompanyId(),
                    vendorFieldsValidationRequest.getLocale(),
                    vendorFieldsValidationRequest.getPartnerCode()));
        };
    }
}
