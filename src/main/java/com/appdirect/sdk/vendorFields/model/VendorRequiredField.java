package com.appdirect.sdk.vendorFields.requiredFields.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorRequiredField {

	private FieldType fieldType;
	private String inputCode;
	private String inputTitle;
	private String toolTip;
	private String subTitle;
	private boolean required;
	private String prePopulatedValue;
	private int order;
}
