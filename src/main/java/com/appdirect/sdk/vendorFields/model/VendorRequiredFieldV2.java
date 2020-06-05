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
class VendorRequiredFieldV2 {
    private String inputCode;
    private String inputTitle;
    private String subTitle;
    private String tooltip;
    private String value;
    private FieldType fieldType;
    private VendorRequiredFieldValidationsV2 validations;
    private VendorRequiredFieldOptionsV2 options;
}
