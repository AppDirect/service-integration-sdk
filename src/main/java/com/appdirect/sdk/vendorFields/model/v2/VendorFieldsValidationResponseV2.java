package com.appdirect.sdk.vendorFields.model.v2;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VendorFieldsValidationResponseV2 {
	private int status;
	private String code;
	private List<ValidationFieldResponse> fields;
}
