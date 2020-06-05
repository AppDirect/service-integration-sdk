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
class Validations {
    private boolean required;
    private boolean readonly;
    private String minLength;
    private String maxLength;
    private String expression;
}
