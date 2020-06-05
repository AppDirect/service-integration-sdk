package com.appdirect.sdk.vendorFields.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VendorFieldsValidationRequestV2 { 
	private String applicationIdentifier;
	private String editionId;
	private FlowType flowType;
	private OperationType operationType;
	private String userId;
	private String companyId;
	private String salesAgentUserId;
	private String salesAgentCompanyId;
	private List<ValidationFieldRequest> fields;
	private String partnerCode;
}
