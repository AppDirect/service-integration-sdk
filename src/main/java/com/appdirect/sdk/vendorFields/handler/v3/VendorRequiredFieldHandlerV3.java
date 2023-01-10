package com.appdirect.sdk.vendorFields.handler.v3;

import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.v3.FlowTypeV3;
import com.appdirect.sdk.vendorFields.model.v3.VendorRequiredFieldsResponseV3;

import java.util.Locale;

/**
 * This is the interface you need to implement to apply vendor required requiredFields on a product
 */
@FunctionalInterface
public interface VendorRequiredFieldHandlerV3 {
    VendorRequiredFieldsResponseV3 getRequiredFields(final String applicationId,
                                                     final String editionId,
                                                     final FlowTypeV3 flowType,
                                                     final OperationType operationType,
                                                     final String userId,
                                                     final String companyId,
                                                     final String salesAgentUserId,
                                                     final String salesAgentCompanyId,
                                                     final Locale locale,
                                                     final String partnerCode
    );
}
