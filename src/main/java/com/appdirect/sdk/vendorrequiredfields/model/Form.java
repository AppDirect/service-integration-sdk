package com.appdirect.sdk.vendorrequiredfields.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Form {

	private List<VendorRequiredField> fields;
	private int order;
}
