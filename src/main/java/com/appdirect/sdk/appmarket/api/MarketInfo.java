package com.appdirect.sdk.appmarket.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MarketInfo implements Serializable {
	private static final long serialVersionUID = 6466997536516437362L;

	private String partner;
	private String baseUrl;
}
