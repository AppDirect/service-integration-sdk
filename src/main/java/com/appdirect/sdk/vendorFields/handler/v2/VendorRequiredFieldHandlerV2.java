package com.appdirect.sdk.vendorFields.handler.v2;

import java.util.List;
import java.util.Locale;

import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponse;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponseV2;

@FunctionalInterface
public interface VendorRequiredFieldHandlerV2 {
    VendorRequiredFieldsResponseV2 getRequiredFields(String applicationId, String editionId, FlowType flowType,
                                                     OperationType operationType, String userId, String companyId,
                                                     String salesAgentUserId, String salesAgentCompanyId,
                                                     List<Locale> locales, String tenant);
}
