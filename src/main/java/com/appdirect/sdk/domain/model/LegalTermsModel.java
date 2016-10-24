package com.appdirect.sdk.domain.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LegalTermsModel implements Serializable {
	private static final long serialVersionUID = 6542111336323952928L;

	private String agreementKey;
	private String title;
	private String url;
	private String content;
}

