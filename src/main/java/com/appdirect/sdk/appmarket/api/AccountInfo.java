package com.appdirect.sdk.appmarket.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo implements Serializable {
	private static final long serialVersionUID = -8495833410980825744L;

	private String accountIdentifier;
	private String status;
}
