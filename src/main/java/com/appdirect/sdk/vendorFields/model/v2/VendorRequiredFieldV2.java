package com.appdirect.sdk.vendorFields.model.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorRequiredFieldV2 {
    private String inputCode;
    private String inputTitle;
    private String subTitle;
    private String tooltip;
    private String value;
    private FieldTypeV2 fieldType;
    private Validations validations;
    private Options options;
    private Boolean visible;
}
