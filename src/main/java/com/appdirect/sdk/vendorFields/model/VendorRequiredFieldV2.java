package com.appdirect.sdk.vendorFields.model;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class VendorRequiredFieldV2 {
    private String inputCode;
    private String inputTitleKey;
    private String subTitleKey;
    private String toolTipKey;
    private String value;
    private FieldType fieldType;
    private VendorRequiredFieldValidationsV2 validations;
    private VendorRequiredFieldOptionsV2 options;
}
