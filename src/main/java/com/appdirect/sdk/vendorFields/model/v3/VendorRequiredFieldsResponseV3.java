package com.appdirect.sdk.vendorFields.model.v3;

import com.appdirect.sdk.vendorFields.model.Context;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorRequiredFieldsResponseV3 {
    private String isvIdentifier;
    private List<VendorRequiredFieldV3> fields;
    private Context context;
}
