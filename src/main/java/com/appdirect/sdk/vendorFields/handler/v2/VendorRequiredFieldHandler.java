package com.appdirect.sdk.vendorFields.handler.v2;

import java.util.List;
import java.util.Locale;

import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.v2.OperationType;
import com.appdirect.sdk.vendorFields.model.v2.VendorRequiredFieldsResponseV2;

/**
 * This is the interface you need to implement to apply vendor required requiredFields on a product
 */
@FunctionalInterface
public interface VendorRequiredFieldHandler {
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
