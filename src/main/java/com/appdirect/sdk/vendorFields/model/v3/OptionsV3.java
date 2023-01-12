package com.appdirect.sdk.vendorFields.model.v3;

import com.appdirect.sdk.vendorFields.model.v2.Suffix;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionsV3 {
    private Suffix suffix;
    private String placeholder;
}
