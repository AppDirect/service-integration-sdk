package com.appdirect.sdk.appmarket.restrictions.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionInfo {
    private String applicationUuid;
    private String externalVendorId;
    private String operationType;
}
