package com.appdirect.sdk.vendorFields.controller.v2;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import com.appdirect.sdk.vendorFields.converter.FlowTypeConverter;
import com.appdirect.sdk.vendorFields.converter.LocaleConverter;
import com.appdirect.sdk.vendorFields.converter.OperationTypeConverter;
import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandler;
import com.appdirect.sdk.vendorFields.handler.v2.VendorRequiredFieldHandlerV2;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponse;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponseV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VendorRequiredFieldV2Controller {
    @Autowired
    private final VendorRequiredFieldHandlerV2 vendorRequiredFieldHandlerv2;

    @RequestMapping(
            method = GET,
            value = "/api/v2/admin/requiredFields",
            produces = APPLICATION_JSON_VALUE)
    public Callable<VendorRequiredFieldsResponseV2> getRequiredFields(
            @RequestParam(value = "applicationId") String applicationId,
            @RequestParam(value = "editionId") String editionId,
            @RequestParam(value = "flowType") FlowType flowType,
            @RequestParam(value = "operationType") OperationType operationType,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "companyId") String companyId,
            @RequestParam(value = "salesAgentUserId") String salesAgentUserId,
            @RequestParam(value = "salesAgentCompanyId") String salesAgentCompanyId,
            @RequestHeader(value = "Accept-Language") List<Locale> locales,
            @RequestHeader(value = "Ad-Tenant") String tenant) {


        log.info(
                "Calling required fields API with applicationId={}, editionId={}, flowType={}, operationType={}, " +
						"userId={}, companyId={}, salesAgentUserId={}, salesAgentCompanyId={}, locales={}, tenant={}",
                applicationId,
                editionId,
                flowType,
                operationType,
                userId,
                companyId,
                salesAgentUserId,
                salesAgentCompanyId,
                locales,
                tenant
        );
        return () -> vendorRequiredFieldHandlerv2.getRequiredFields(applicationId, editionId, flowType, operationType
				, userId, companyId, salesAgentUserId, salesAgentCompanyId, locales, tenant);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(FlowType.class, new FlowTypeConverter());
        webdataBinder.registerCustomEditor(OperationType.class, new OperationTypeConverter());
        webdataBinder.registerCustomEditor(List.class, new LocaleConverter());
    }
}
