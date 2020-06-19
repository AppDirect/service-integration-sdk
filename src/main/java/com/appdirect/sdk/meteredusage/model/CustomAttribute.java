package com.appdirect.sdk.meteredusage.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomAttribute implements Serializable {
	private String attributeType;
	private String name;
	private String value;
}
