package com.appdirect.sdk.vendorrequiredfields.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorRequiredField {

	private FieldType fieldType;
	private String inputCode;
	private String inputTitle;
	private String toolTip;
	private String subTitle;
	private boolean required;
}
