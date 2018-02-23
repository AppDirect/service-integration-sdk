package com.appdirect.sdk.security.openid;

import org.junit.Test;
import org.springframework.security.openid.OpenIDAttribute;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomAxFetchListFactoryTest {

    private static final String EMAIL = "email";
    private static final String ROLES = "roles";
    private static final String FULLNAME = "fullname";
    private static final String OPENID_TYPE_EMAIL = "http://axschema.org/contact/email";
    private static final String OPENID_TYPE_ROLES = "https://www.appdirect.com/schema/user/roles";
    private static final String OPENID_TYPE_FULLNAME = "http://axschema.org/namePerson";
    // Cannot use 0 for unlimited roles because of a bug in the OpenID4Java so using 50 as an alternative
    private static final Integer UNLIMITED_ROLES = 50;

    @Test
    public void testCreateAttributeList() throws Exception {
        CustomAxFetchListFactory customAxFetchListFactory = new CustomAxFetchListFactory();
        List<OpenIDAttribute> attributeList = customAxFetchListFactory.createAttributeList("anything");

        assertThat(attributeList).hasSize(3);
        assertThat(attributeList).extracting(OpenIDAttribute::getName).containsOnly(EMAIL, ROLES, FULLNAME);
        assertThat(attributeList).extracting(OpenIDAttribute::getType).containsOnly(OPENID_TYPE_EMAIL, OPENID_TYPE_FULLNAME, OPENID_TYPE_ROLES);
        assertThat(attributeList).extracting(OpenIDAttribute::isRequired).containsOnly(true);
        assertAttributeCountEqualsTo(attributeList, ROLES, UNLIMITED_ROLES);
    }

    private void assertAttributeCountEqualsTo(List<OpenIDAttribute> attributeList, String attributeName, int expectedCount) {
        assertThat(attributeList.stream().filter(attribute -> attribute.getName().equals(attributeName))
                .map(OpenIDAttribute::getCount)
                .findFirst())
                .contains(expectedCount);
    }
}
