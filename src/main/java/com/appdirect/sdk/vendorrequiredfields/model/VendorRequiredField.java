package com.appdirect.sdk.vendorrequiredfields.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorRequiredField {

	private FieldType fieldType;
	private String inputCode;
	private String inputTitle;
	private String toolTip;
	private String subTitle;
	private boolean required;
	private String prePopulatedValue;
	private int order;

	public VendorRequiredField(String fieldType, String inputCode, String inputTitle, String toolTip, String subTitle, boolean required, int order) {
		this.fieldType = FieldType.valueOf(fieldType);
		this.inputCode = inputCode;
		this.inputTitle = inputTitle;
		this.toolTip = toolTip;
		this.subTitle = subTitle;
		this.required = required;
		this.order = order;
	}
}
