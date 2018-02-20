package com.appdirect.sdk.web.openid;

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

    private List<OpenIDAttribute> openIDAttributes;

    public CustomAxFetchListFactory() {
        OpenIDAttribute emailAttribute = new OpenIDAttribute(EMAIL, OPENID_TYPE_EMAIL);
        OpenIDAttribute rolesAttribute = new OpenIDAttribute(ROLES, OPENID_TYPE_ROLES);
        // Cannot use 0 for unlimited roles because of a bug in the OpenID4Java so using 50 as an alternative
        rolesAttribute.setCount(50);
        OpenIDAttribute fullnameAttribute = new OpenIDAttribute(FULLNAME, OPENID_TYPE_FULLNAME);

        openIDAttributes = Arrays.asList(emailAttribute, rolesAttribute, fullnameAttribute);
        openIDAttributes.forEach(attribute -> attribute.setRequired(true));
    }

    @Override
    public List<OpenIDAttribute> createAttributeList(String identifier) {
        return this.openIDAttributes;
    }
}
