package com.appdirect.sdk.vendorFields.model.v2;

import com.appdirect.sdk.vendorFields.model.FlowTypeV2;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VendorFieldsValidationRequestV2 {
	private String applicationId;
	private String editionId;
	private FlowTypeV2 flowType;
	private OperationType operationType;
	private String userId;
	private String companyId;
	private String salesAgentUserId;
	private String salesAgentCompanyId;
	private List<ValidationFieldRequest> fields;
	private String partnerCode;
	private List<Locale> locales;
}
