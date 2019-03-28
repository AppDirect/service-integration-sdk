package com.appdirect.sdk.vendorFields.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
