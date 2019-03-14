package com.appdirect.sdk.vendorFields.requiredFields.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Form {

	private List<VendorRequiredField> fields;
	private int order;
}
