package com.appdirect.isv.api.model.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfo implements Serializable {
	private static final long serialVersionUID = -3689138943301029315L;

	private String uuid;
	private String name;
	private String email;
	private String phoneNumber;
	private String website;
	private String country;
}
