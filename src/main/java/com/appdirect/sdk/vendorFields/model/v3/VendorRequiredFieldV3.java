package com.appdirect.sdk.vendorFields.model.v3;

import com.appdirect.sdk.vendorFields.model.v2.Validations;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.appdirect.sdk.vendorFields.model.v3.OptionsV3;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorRequiredFieldV3 {
    private String inputCode;
    private String inputTitle;
    private String subTitle;
    private String tooltip;
    private String value;
    private FieldTypeV3 fieldType;
    private Validations validations;
    private OptionsV3 options;
    private Boolean visible;
}
