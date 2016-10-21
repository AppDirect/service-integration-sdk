package com.appdirect.sdk.isv.api.model.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillingAPIResult implements Serializable {
	private static final long serialVersionUID = -7027507409588330850L;

	private boolean success;
	private String message;
}
