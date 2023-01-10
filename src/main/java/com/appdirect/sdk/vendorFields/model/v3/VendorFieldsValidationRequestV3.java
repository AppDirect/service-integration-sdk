package com.appdirect.sdk.vendorFields.model.v3;

import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.v2.ValidationFieldRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VendorFieldsValidationRequestV3 {

	private String applicationId;
	private String editionId;
	private FlowTypeV3 flowType;
	private OperationType operationType;
	private String userId;
	private String companyId;
	private String salesAgentUserId;
	private String salesAgentCompanyId;
	private List<ValidationFieldRequest> fields;
	private String partnerCode;
	private Locale locale;
}
