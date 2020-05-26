package com.appdirect.sdk.vendorFields.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VendorRequiredFieldValidationsV2 {
    private Boolean required = true;
    private Boolean readonly = false;
    private String minLength = "2";
    private String maxLength = "255";
    private String expression = null;
}
