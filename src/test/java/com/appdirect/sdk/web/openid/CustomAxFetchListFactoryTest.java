package com.appdirect.sdk.web.openid;

import org.springframework.security.openid.OpenIDAttribute;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomAxFetchListFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCreateAttributeList() throws Exception {
        CustomAxFetchListFactory customAxFetchListFactory = new CustomAxFetchListFactory();
        List<OpenIDAttribute> attributeList = customAxFetchListFactory.createAttributeList("anything");

        assertThat(attributeList).isNotNull().isNotEmpty();
    }
}
