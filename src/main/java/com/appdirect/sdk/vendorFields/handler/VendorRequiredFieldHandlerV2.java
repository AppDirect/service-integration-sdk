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
    VendorRequiredFieldsResponseV2 getRequiredFields(final String applicationId,
                                                     final String editionId,
                                                     final FlowType flowType,
                                                     final OperationType operationType,
                                                     final String userId,
                                                     final String companyId,
                                                     final String salesAgentUserId,
                                                     final String salesAgentCompanyId,
                                                     final List<Locale> locales,
                                                     final String partnerCode
    );
}
