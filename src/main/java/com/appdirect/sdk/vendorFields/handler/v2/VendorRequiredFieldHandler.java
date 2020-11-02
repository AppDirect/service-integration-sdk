package com.appdirect.sdk.vendorFields.handler.v2;

import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.v2.FlowTypeV2;
import com.appdirect.sdk.vendorFields.model.v2.VendorRequiredFieldsResponseV2;
import java.util.Locale;
import java.util.Map;

/**
 * This is the interface you need to implement to apply vendor required requiredFields on a product
 */
@FunctionalInterface
public interface VendorRequiredFieldHandler {
    VendorRequiredFieldsResponseV2 getRequiredFields(final String applicationId,
                                                     final String editionId,
                                                     final FlowTypeV2 flowType,
                                                     final OperationType operationType,
                                                     final String userId,
                                                     final String companyId,
                                                     final String salesAgentUserId,
                                                     final String salesAgentCompanyId,
                                                     final Locale locale,
                                                     final String partnerCode,
                                                     final Map<String, String> additionalInformation
    );
}
