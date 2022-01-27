package com.appdirect.sdk.vendorFields.model.v2;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorFieldsValidationResponseV2 {
	private int status;
	private String code;
	private List<ValidationFieldResponse> fields;
}
