package com.appdirect.sdk.vendorFields.model.v2;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VendorRequiredFieldsResponseV2 {
    private String isvIdentifier;
    private List<VendorRequiredFieldV2> fields;
}
