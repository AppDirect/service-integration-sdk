package com.appdirect.sdk.security.openid;

import org.springframework.security.openid.AxFetchListFactory;
import org.springframework.security.openid.OpenIDAttribute;

import java.util.Arrays;
import java.util.List;

public class CustomAxFetchListFactory implements AxFetchListFactory {

    private static final String EMAIL = "email";
    private static final String ROLES = "roles";
    private static final String FULLNAME = "fullname";
    private static final String OPENID_TYPE_EMAIL = "http://axschema.org/contact/email";
    private static final String OPENID_TYPE_ROLES = "https://www.appdirect.com/schema/user/roles";
    private static final String OPENID_TYPE_FULLNAME = "http://axschema.org/namePerson";
    // Cannot use 0 for unlimited roles because of a bug in the OpenID4Java so using 50 as an alternative
    private static final Integer UNLIMITED_ROLES = 50;

    private final List<OpenIDAttribute> openIDAttributes;

    public CustomAxFetchListFactory() {
        OpenIDAttribute emailAttribute = new OpenIDAttribute(EMAIL, OPENID_TYPE_EMAIL);
        emailAttribute.setRequired(true);
        OpenIDAttribute rolesAttribute = new OpenIDAttribute(ROLES, OPENID_TYPE_ROLES);
        rolesAttribute.setRequired(true);
        rolesAttribute.setCount(UNLIMITED_ROLES);
        OpenIDAttribute fullnameAttribute = new OpenIDAttribute(FULLNAME, OPENID_TYPE_FULLNAME);
        fullnameAttribute.setRequired(true);

        openIDAttributes = Arrays.asList(emailAttribute, rolesAttribute, fullnameAttribute);
    }

    @Override
    public List<OpenIDAttribute> createAttributeList(String identifier) {
        return this.openIDAttributes;
    }
}
