package com.appdirect.sdk.vendorFields.model;

import java.util.List;

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
class FormV2 {
    private String isvIdentifier;
    private Context context;
    private String title;
    private String subTitle;
    private List<VendorRequiredFieldV2> fields;
}
