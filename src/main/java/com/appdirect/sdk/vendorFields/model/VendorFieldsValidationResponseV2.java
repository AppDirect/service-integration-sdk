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
public class VendorFieldsValidationResponseV2 { // TODO Ajay: extend from VendorFieldsValidationResponseV2 to avoid duplicates?
	List<VendorFieldValidation> validations;
}
