package com.appdirect.sdk.meteredusage.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeItem {
	private Map<String, String> attributes = null;
}

