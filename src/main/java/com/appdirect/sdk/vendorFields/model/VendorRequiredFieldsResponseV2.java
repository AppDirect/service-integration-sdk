package com.appdirect.sdk.vendorFields.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VendorRequiredFieldsResponseV2 {
    // https://appdirect.jira.com/wiki/spaces/PI/pages/982647318/TD+Required+field+Service#3.2.1.1.4-Response
    private String isvIdentifier;
    private List<Form> forms;
}
