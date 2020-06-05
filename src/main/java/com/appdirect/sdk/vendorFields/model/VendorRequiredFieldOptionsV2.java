package com.appdirect.sdk.vendorFields.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class VendorRequiredFieldOptionsV2 {
    private VendorRequiredFieldSuffixV2 suffix;
    private String placeholder;
}
