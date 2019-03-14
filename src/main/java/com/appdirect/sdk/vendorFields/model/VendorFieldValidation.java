package com.appdirect.sdk.vendorFields.fieldsValidation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorFieldValidation {
	String fieldName;
	String errorMessage;
}
