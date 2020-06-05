package com.appdirect.sdk.vendorFields.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.appdirect.sdk.vendorFields.handler.VendorFieldValidationHandlerV2;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationRequest;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationRequestV2;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationResponse;
import com.appdirect.sdk.vendorFields.model.VendorFieldsValidationResponseV2;

/**
 * Defines the endpoint for validating the fields with the vendor
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class VendorFieldValidationController {

    @Autowired
    private final VendorFieldValidationHandler vendorFieldValidationHandler;

    @Autowired
    private final VendorFieldValidationHandlerV2 vendorFieldValidationHandlerV2;

    @RequestMapping(
            method = POST,
            value = "/api/v1/admin/vendorValidations",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Callable<VendorFieldsValidationResponse> validateFields(
            @RequestBody VendorFieldsValidationRequest vendorFieldsValidationRequest,
            @RequestHeader(value = "Accept-Language") List<Locale> locales) {

        log.info(
                "Calling validate fields API with editionId={}, flowType={}, operationType={}, partner={}, applicationIdentifier={}, locales={}",
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
            @RequestHeader(value = "AD-Tenant") final String partnerCode) {

        log.info(
                "Calling validate fields API with " +
                        "applicationIdentifier={}, " +
                        "editionId={}, " +
                        "flowType={}, " +
                        "operationType={}, " +
                        "userId={}, " +
                        "companyId={}, " +
                        "salesAgentUserId={}, " +
                        "salesAgentCompanyId={}, " +
                        "partnerCode={}, " +
                        "is not supported.",
                vendorFieldsValidationRequest.getApplicationIdentifier(),
                vendorFieldsValidationRequest.getEditionId(),
                vendorFieldsValidationRequest.getFlowType(),
                vendorFieldsValidationRequest.getOperationType(),
                vendorFieldsValidationRequest.getUserId(),
                vendorFieldsValidationRequest.getCompanyId(),
                vendorFieldsValidationRequest.getSalesAgentUserId(),
                vendorFieldsValidationRequest.getSalesAgentCompanyId(),
                vendorFieldsValidationRequest.getPartnerCode()
        );

        vendorFieldsValidationRequest.setPartnerCode(partnerCode);
        return () -> vendorFieldValidationHandlerV2.validateFields(vendorFieldsValidationRequest);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(FlowType.class, new FlowTypeConverter());
        webdataBinder.registerCustomEditor(OperationType.class, new OperationTypeConverter());
        webdataBinder.registerCustomEditor(List.class, new LocaleConverter());
    }
}
