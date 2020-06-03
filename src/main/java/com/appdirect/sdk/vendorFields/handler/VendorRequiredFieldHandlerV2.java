package com.appdirect.sdk.vendorFields.handler;

import java.util.List;
import java.util.Locale;

import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponseV2;

/**
 * This is the interface you need to implement to apply vendor required requiredFields on a product
 */
@FunctionalInterface
public interface VendorRequiredFieldHandlerV2 {
    // https://appdirect.jira.com/wiki/spaces/PI/pages/982647318/TD+Required+field+Service#3.2.1.1.3-Parameters
    VendorRequiredFieldsResponseV2 getRequiredFields(String applicationId,
                                                     String editionId,
                                                     FlowType flowType,
                                                     OperationType operationType,
                                                     String userId,
                                                     String companyId,
                                                     String salesAgentUserId,
                                                     String salesAgentCompanyId,
                                                     List<Locale> locales
    );
}
