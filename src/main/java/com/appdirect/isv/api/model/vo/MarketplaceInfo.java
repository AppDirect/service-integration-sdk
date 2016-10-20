package com.appdirect.isv.api.model.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MarketplaceInfo implements Serializable {
	private static final long serialVersionUID = 6466997536516437362L;

	private String partner;
	private String baseUrl;
}
