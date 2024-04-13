package com.appdirect.sdk.vendorFields.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.bind.WebDataBinder;

import com.appdirect.sdk.vendorFields.converter.FlowTypeConverter;
import com.appdirect.sdk.vendorFields.converter.FlowTypeV2Converter;
import com.appdirect.sdk.vendorFields.converter.LocaleConverter;
import com.appdirect.sdk.vendorFields.converter.OperationTypeConverter;
import com.appdirect.sdk.vendorFields.handler.VendorRequiredFieldHandler;
import com.appdirect.sdk.vendorFields.model.Context;
import com.appdirect.sdk.vendorFields.model.FieldType;
import com.appdirect.sdk.vendorFields.model.FlowType;
import com.appdirect.sdk.vendorFields.model.Form;
import com.appdirect.sdk.vendorFields.model.OperationType;
import com.appdirect.sdk.vendorFields.model.VendorRequiredField;
import com.appdirect.sdk.vendorFields.model.VendorRequiredFieldsResponse;
import com.appdirect.sdk.vendorFields.model.v2.FieldTypeV2;
import com.appdirect.sdk.vendorFields.model.v2.FlowTypeV2;
import com.appdirect.sdk.vendorFields.model.v2.Options;
import com.appdirect.sdk.vendorFields.model.v2.Suffix;
import com.appdirect.sdk.vendorFields.model.v2.Validations;
import com.appdirect.sdk.vendorFields.model.v2.VendorRequiredFieldV2;
import com.appdirect.sdk.vendorFields.model.v2.VendorRequiredFieldsResponseV2;

@RunWith(MockitoJUnitRunner.class)
public class VendorRequiredFieldsControllerTest {

    @Mock
    private VendorRequiredFieldHandler mockVendorRequiredFieldHandler;

    @Mock
    private com.appdirect.sdk.vendorFields.handler.v2.VendorRequiredFieldHandler mockVendorRequiredFieldHandlerV2;

    @Mock
    private com.appdirect.sdk.vendorFields.handler.v3.VendorRequiredFieldHandlerV3 mockVendorRequiredFieldHandlerV3;

    @Mock
    private WebDataBinder webdataBinder;

    private VendorRequiredFieldController vendorRequiredFieldController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorRequiredFieldController = new VendorRequiredFieldController(mockVendorRequiredFieldHandler, mockVendorRequiredFieldHandlerV2, mockVendorRequiredFieldHandlerV3);
    }

    @Test
    public void testGetRequiredFields_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
        //Given
        List<Locale> locales = Collections.singletonList(Locale.US);
        Form form = Form.builder()
                .fields(Collections.singletonList(VendorRequiredField.builder()
                        .fieldType(FieldType.COUNTRY)
                        .inputCode("ABCDE")
                        .inputTitle("COUNTRY")
                        .order(1)
                        .prePopulatedValue("GERMANY")
                        .required(true)
                        .subTitle("REGION")
                        .toolTip("place your country")
                        .build()))
                .order(4)
                .build();
        VendorRequiredFieldsResponse response = new VendorRequiredFieldsResponse(Collections.singletonList(form));
        when(mockVendorRequiredFieldHandler.getRequiredFields(
                "SKU",
                FlowType.RESELLER_FLOW,
                OperationType.SUBSCRIPTION_CHANGE,
                locales)).thenReturn(response);

        //When
        VendorRequiredFieldsResponse controllerResponse = vendorRequiredFieldController
                .getRequiredFields(
                        "SKU",
                        FlowType.RESELLER_FLOW,
                        OperationType.SUBSCRIPTION_CHANGE,
                        locales).call();

        //Then
        assertThat(controllerResponse).isEqualTo(response);
    }

    @Test
    public void testGetRequiredFieldsV2_whenCalled_thenControllerForwardsItsArgumentsToTheUnderlyingHandler() throws Exception {
        //Given
        final String partnerCode = "AD-Tenant";
        final Validations validations = Validations.builder()
                .required(true)
                .readonly(true)
                .minLength(2)
                .maxLength(255)
                .expression("expression")
                .build();
        final Suffix suffix = Suffix.builder()
                .text("text")
                .inputCode("inputCode")
                .build();
        final Options options = Options.builder()
                .suffix(suffix)
                .placeholder("placeholder")
                .build();
        final VendorRequiredFieldV2 vendorRequiredFieldV2 = VendorRequiredFieldV2.builder()
                .inputCode("ADDRESS_POSTAL_CODE")
                .inputTitle("inputTitle")
                .subTitle("subTitle")
                .tooltip("tooltip")
                .value("value")
                .fieldType(FieldTypeV2.COUNTRY)
                .validations(validations)
                .options(options)
                .visible(true)
                .build();
        final VendorRequiredFieldsResponseV2 response = new VendorRequiredFieldsResponseV2("isvIdentifier", Collections.singletonList(vendorRequiredFieldV2), Context.CART_LEVEL);
        when(mockVendorRequiredFieldHandlerV2.getRequiredFields(
                "SKU",
                "editionId",
                FlowTypeV2.BOBO,
                OperationType.SUBSCRIPTION_CHANGE,
                "userId",
                "companyId",
                "salesAgentUserId",
                "salesAgentCompanyId",
                Locale.US,
                partnerCode)).thenReturn(response);

        //When
        VendorRequiredFieldsResponseV2 controllerResponse = vendorRequiredFieldController
                .getRequiredFields(
                        "SKU",
                        "editionId",
                        FlowTypeV2.BOBO,
                        OperationType.SUBSCRIPTION_CHANGE,
                        "userId",
                        "companyId",
                        "salesAgentUserId",
                        "salesAgentCompanyId",
                        Locale.US,
                        partnerCode
                ).call();

        //Then
        assertThat(controllerResponse).isEqualTo(response);
    }

    @Test
    public void testInitBinder() {
        vendorRequiredFieldController.initBinder(webdataBinder);

        verify(webdataBinder, times(1)).registerCustomEditor(eq(FlowType.class), any(FlowTypeConverter.class));
        verify(webdataBinder, times(1)).registerCustomEditor(eq(FlowTypeV2.class), any(
            FlowTypeV2Converter.class));
        verify(webdataBinder, times(1)).registerCustomEditor(eq(OperationType.class), any(OperationTypeConverter.class));
        verify(webdataBinder, times(1)).registerCustomEditor(eq(List.class), any(LocaleConverter.class));
    }
}
