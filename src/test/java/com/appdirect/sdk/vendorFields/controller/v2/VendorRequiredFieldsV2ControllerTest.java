package com.appdirect.sdk.vendorFields.controller.v2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.appdirect.sdk.vendorFields.controller.VendorRequiredFieldController;
import com.appdirect.sdk.vendorFields.converter.FlowTypeConverter;
import com.appdirect.sdk.vendorFields.converter.LocaleConverter;
import com.appdirect.sdk.vendorFields.converter.OperationTypeConverter;
import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandler;
import com.appdirect.sdk.vendorFields.handler.v2.VendorRequiredFieldHandlerV2;
import com.appdirect.sdk.vendorFields.model.FieldType;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.Form;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorRequiredField;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldOptionsV2;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldSuffixV2;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldV2;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldValidationsV2;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponse;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponseV2;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.bind.WebDataBinder;

@RunWith(MockitoJUnitRunner.class)
public class VendorRequiredFieldsV2ControllerTest {
    @Mock
    private VendorRequiredFieldHandlerV2 mockVendorRequiredFieldHandlerV2;
    @Mock
    private WebDataBinder webdataBinder;

    private VendorRequiredFieldV2Controller vendorRequiredFieldV2Controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorRequiredFieldV2Controller = new VendorRequiredFieldV2Controller(mockVendorRequiredFieldHandlerV2);
    }

    @Test
    public void testValidateFields_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
        //Given
        List<Locale> locales = Collections.singletonList(Locale.US);
        VendorRequiredFieldV2 vendorRequiredFieldV2 = VendorRequiredFieldV2.builder()
                .inputCode("DOMAIN")
                .inputTitleKey("field.domain.title")
                .subTitleKey("field.domain.subtitle")
                .toolTipKey("field.domain.tooltip")
                .fieldType(FieldType.DOMAIN)
                .validations(VendorRequiredFieldValidationsV2.builder()
                        .required(true)
                        .readonly(false)
                        .build())
                .options(VendorRequiredFieldOptionsV2.builder()
                        .suffix(VendorRequiredFieldSuffixV2.builder()
                                .textKey("filed.domain.options.suffix.text")
                                .build())
                        .placeholder("field.domain.options.placeholder")
                        .build())
                .build();
        VendorRequiredFieldsResponseV2 response = new VendorRequiredFieldsResponseV2(StringUtils.EMPTY,
                Collections.singletonList(vendorRequiredFieldV2));
        when(mockVendorRequiredFieldHandlerV2.getRequiredFields(
                anyString(),
                anyString(),
                any(FlowType.class),
                any(OperationType.class),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(),
                anyString())).thenReturn(response);

        //When
        VendorRequiredFieldsResponseV2 controllerResponse = vendorRequiredFieldV2Controller
                .getRequiredFields(
                        UUID.randomUUID().toString(),
                        "SKU",
                        FlowType.RESELLER_FLOW,
                        OperationType.SUBSCRIPTION_CHANGE,
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        locales,
                        "APPDIRECT").call();

        //Then
        assertThat(controllerResponse).isEqualTo(response);
    }

    @Test
    public void testInitBinder() {
        vendorRequiredFieldV2Controller.initBinder(webdataBinder);

        verify(webdataBinder, times(1)).registerCustomEditor(eq(FlowType.class), any(FlowTypeConverter.class));
        verify(webdataBinder, times(1)).registerCustomEditor(eq(OperationType.class),
                any(OperationTypeConverter.class));
        verify(webdataBinder, times(1)).registerCustomEditor(eq(List.class), any(LocaleConverter.class));
    }
}
