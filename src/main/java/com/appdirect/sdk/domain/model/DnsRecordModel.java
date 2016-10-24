package com.appdirect.sdk.domain.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DnsRecordModel implements Serializable {
	private static final long serialVersionUID = 7149783858521513203L;

	private String type;
	private String name;
	private String data;
	private Long ttl;
}
