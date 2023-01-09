package com.appdirect.sdk.vendorFields.model.v3;

import com.appdirect.sdk.vendorFields.model.v2.ValidationFieldResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorFieldsValidationResponseV3 {
	private int status;
	private String code;
	private List<ValidationFieldResponse> fields;
}
