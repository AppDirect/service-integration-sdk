package com.appdirect.sdk.vendorFields.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.appdirect.sdk.vendorFields.converter.FlowTypeV2Converter;
import com.appdirect.sdk.vendorFields.converter.LocaleObjectConverter;
import com.appdirect.sdk.vendorFields.model.v2.FlowTypeV2;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import com.appdirect.sdk.vendorFields.model.v3.VendorFieldsValidationRequestV3;
import com.appdirect.sdk.vendorFields.model.v3.VendorFieldsValidationResponseV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.vendorFields.converter.FlowTypeConverter;
import com.appdirect.sdk.vendorFields.converter.LocaleConverter;
import com.appdirect.sdk.vendorFields.converter.OperationTypeConverter;
import com.appdirect.sdk.vendorFields.handler.VendorFieldValidationHandler;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationRequest;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationResponse;
import com.appdirect.sdk.vendorFields.model.v2.VendorFieldsValidationRequestV2;
import com.appdirect.sdk.vendorFields.model.v2.VendorFieldsValidationResponseV2;

/**
 * Defines the endpoint for validating the fields with the vendor
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class VendorFieldValidationController {

    private final VendorFieldValidationHandler vendorFieldValidationHandler;
    private final com.appdirect.sdk.vendorFields.handler.v2.VendorFieldValidationHandler vendorFieldValidationHandlerV2;
    private final com.appdirect.sdk.vendorFields.handler.v3.VendorFieldValidationHandlerV3 vendorFieldValidationHandlerV3;


    @RequestMapping(
            method = POST,
            value = "/api/v1/admin/vendorValidations",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Callable<VendorFieldsValidationResponse> validateFields(
            @RequestBody VendorFieldsValidationRequest vendorFieldsValidationRequest,
            @RequestHeader(value = "Accept-Language") List<Locale> locales) {

        log.info(
                "Calling validate fields API with editionCode={}, flowType={}, operationType={}, partner={}, applicationIdentifier={}, locales={}",
                vendorFieldsValidationRequest.getEditionCode(),
                vendorFieldsValidationRequest.getFlowType(),
                vendorFieldsValidationRequest.getOperationType(),
                vendorFieldsValidationRequest.getPartner(),
                vendorFieldsValidationRequest.getApplicationIdentifier(),
                locales
        );

        vendorFieldsValidationRequest.setLocales(locales);
        return () -> vendorFieldValidationHandler.validateFields(vendorFieldsValidationRequest);
    }

    @RequestMapping(
            method = POST,
            value = "/api/v2/admin/vendorValidations",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Callable<VendorFieldsValidationResponseV2> validateFields(
            @RequestBody final VendorFieldsValidationRequestV2 vendorFieldsValidationRequest,
            @RequestHeader(value = "AD-Tenant") final String partnerCode,
            @RequestHeader(required = false, value = "Accept-Language") Locale locale) {

        log.info(
                "Calling validate fields API with " +
                        "applicationId={}, " +
                        "editionId={}, " +
                        "flowType={}, " +
                        "operationType={}, " +
                        "userId={}, " +
                        "companyId={}, " +
                        "salesAgentUserId={}, " +
                        "salesAgentCompanyId={}, " +
                        "locales={}, " +
                        "partnerCode={}",
                vendorFieldsValidationRequest.getApplicationId(),
                vendorFieldsValidationRequest.getEditionId(),
                vendorFieldsValidationRequest.getFlowType(),
                vendorFieldsValidationRequest.getOperationType(),
                vendorFieldsValidationRequest.getUserId(),
                vendorFieldsValidationRequest.getCompanyId(),
                vendorFieldsValidationRequest.getSalesAgentUserId(),
                vendorFieldsValidationRequest.getSalesAgentCompanyId(),
                locale,
                partnerCode
        );

        vendorFieldsValidationRequest.setLocale(locale);
        vendorFieldsValidationRequest.setPartnerCode(partnerCode);
        return () -> vendorFieldValidationHandlerV2.validateFields(vendorFieldsValidationRequest);
    }

    @RequestMapping(
            method = POST,
            value = "/api/v3/admin/vendorValidations",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Callable<VendorFieldsValidationResponseV3> validateFields(
            @RequestBody final VendorFieldsValidationRequestV3 vendorFieldsValidationRequest,
            @RequestHeader(value = "AD-Tenant") final String partnerCode,
            @RequestHeader(required = false, value = "Accept-Language") Locale locale) {

        log.info(
                "Calling validate fields API with " +
                        "applicationId={}, " +
                        "editionId={}, " +
                        "flowType={}, " +
                        "operationType={}, " +
                        "userId={}, " +
                        "companyId={}, " +
                        "salesAgentUserId={}, " +
                        "salesAgentCompanyId={}, " +
                        "locales={}, " +
                        "partnerCode={}",
                vendorFieldsValidationRequest.getApplicationId(),
                vendorFieldsValidationRequest.getEditionId(),
                vendorFieldsValidationRequest.getFlowType(),
                vendorFieldsValidationRequest.getOperationType(),
                vendorFieldsValidationRequest.getUserId(),
                vendorFieldsValidationRequest.getCompanyId(),
                vendorFieldsValidationRequest.getSalesAgentUserId(),
                vendorFieldsValidationRequest.getSalesAgentCompanyId(),
                locale,
                partnerCode
        );

        vendorFieldsValidationRequest.setLocale(locale);
        vendorFieldsValidationRequest.setPartnerCode(partnerCode);
        return () -> vendorFieldValidationHandlerV3.validateFields(vendorFieldsValidationRequest);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(FlowType.class, new FlowTypeConverter());
        webdataBinder.registerCustomEditor(FlowTypeV2.class, new FlowTypeV2Converter());
        webdataBinder.registerCustomEditor(OperationType.class, new OperationTypeConverter());
        webdataBinder.registerCustomEditor(List.class, new LocaleConverter());
        webdataBinder.registerCustomEditor(Locale.class, new LocaleObjectConverter());
    }
}
