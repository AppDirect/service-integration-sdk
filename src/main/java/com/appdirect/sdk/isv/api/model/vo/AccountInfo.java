package com.appdirect.sdk.isv.api.model.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo implements Serializable {
	private static final long serialVersionUID = -8495833410980825744L;

	private String accountIdentifier;
	private String status;
}
