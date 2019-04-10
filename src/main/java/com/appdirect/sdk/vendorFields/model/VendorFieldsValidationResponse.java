package com.appdirect.sdk.vendorFields.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VendorFieldsValidationResponse {
	List<VendorFieldValidation> validations;
}
