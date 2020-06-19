package com.appdirect.sdk.vendorFields.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.appdirect.sdk.vendorFields.converter.FlowTypeConverter;
import com.appdirect.sdk.vendorFields.converter.FlowTypeV2Converter;
import com.appdirect.sdk.vendorFields.converter.LocaleConverter;
import com.appdirect.sdk.vendorFields.converter.OperationTypeConverter;
import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandler;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.v2.FlowTypeV2;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponse;
import com.appdirect.sdk.vendorFields.model.v2.VendorRequiredFieldsResponseV2;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Defines the endpoint for enforcing vendor required requiredFields on their products
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class VendorRequiredFieldController {

    private final VendorRequiredFieldHandler vendorRequiredFieldHandler;
    private final com.appdirect.sdk.vendorFields.handler.v2.VendorRequiredFieldHandler vendorRequiredFieldHandlerV2;

    @RequestMapping(
            method = GET,
            value = "/api/v1/admin/requiredFields",
            produces = APPLICATION_JSON_VALUE)
    public Callable<VendorRequiredFieldsResponse> getRequiredFields(
            @RequestParam(value = "editionCode") String editionCode,
            @RequestParam(value = "flowType") FlowType flowType,
            @RequestParam(value = "operationType") OperationType operationType,
            @RequestHeader(value = "Accept-Language") List<Locale> locales) {

        log.info(
                "Calling required fields API with editionCode={}, flowType={}, operationType={}, locales={}",
                editionCode,
                flowType,
                operationType,
                locales
        );
        return () -> vendorRequiredFieldHandler.getRequiredFields(editionCode, flowType, operationType, locales);
    }

    @RequestMapping(
            method = GET,
            value = "/api/v2/admin/requiredFields",
            produces = APPLICATION_JSON_VALUE)
    public Callable<VendorRequiredFieldsResponseV2> getRequiredFields(
            @RequestParam(value = "applicationId") final String applicationId,
            @RequestParam(value = "editionId") final String editionId,
            @RequestParam(value = "flowType") final FlowTypeV2 flowType,
            @RequestParam(value = "operationType") final OperationType operationType,
            @RequestParam(value = "userId") final String userId,
            @RequestParam(value = "companyId") final String companyId,
            @RequestParam(required = false, value = "salesAgentUserId") final String salesAgentUserId,
            @RequestParam(required = false, value = "salesAgentCompanyId") final String salesAgentCompanyId,
            @RequestHeader(value = "Accept-Language") final Locale locale,
            @RequestHeader(value = "AD-Tenant") final String partnerCode) {

        log.info(
                "Calling required fields API with " +
                        "applicationId={}, " +
                        "editionId={}, " +
                        "flowType={}, " +
                        "operationType={}, " +
                        "userId={}, " +
                        "companyId={}, " +
                        "salesAgentUserId={}, " +
                        "salesAgentCompanyId={}" +
                        "locales={}" +
                        "partnerCode={}",
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
        );
        return () -> vendorRequiredFieldHandlerV2.getRequiredFields(
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
        );
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(FlowType.class, new FlowTypeConverter());
        webdataBinder.registerCustomEditor(FlowTypeV2.class, new FlowTypeV2Converter());
        webdataBinder.registerCustomEditor(OperationType.class, new OperationTypeConverter());
        webdataBinder.registerCustomEditor(List.class, new LocaleConverter());
    }
}
