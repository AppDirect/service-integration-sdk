package com.appdirect.sdk.vendorFields.model.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.appdirect.sdk.vendorFields.model.v2.InputCode;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValidationFieldResponse {
    private InputCode inputCode;
    private String messageKey;
}
