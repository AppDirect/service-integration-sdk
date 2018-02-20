package com.appdirect.sdk.web.openid;

import com.appdirect.sdk.utils.RoleMapper;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.List;

public class CustomUserDetailsService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private static final String ROLES = "roles";
    private static final String FULLNAME = "fullname";
    private static final String EMAIL = "email";

    private final RoleMapper roleMapper;

    public CustomUserDetailsService(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public UserDetails loadUserDetails(OpenIDAuthenticationToken openIDAuthenticationToken) throws UsernameNotFoundException {
        List<OpenIDAttribute> attributes = openIDAuthenticationToken.getAttributes();

        OpenIdUserDetails user = new OpenIdUserDetails();
        user.setUsername(getFullName(attributes));
        user.setEmail(getEmail(attributes));
        user.setRoles(roleMapper.roleMap(getGrantedAuthorities(attributes)));

        return user;
    }

    List<String> getGrantedAuthorities(List<OpenIDAttribute> attributes) {
        return getAttributeValues(attributes, ROLES);
    }

    String getFullName(List<OpenIDAttribute> attributes) {
        return getAttributeValues(attributes, FULLNAME).get(0);
    }

    String getEmail(List<OpenIDAttribute> attributes) {
        return getAttributeValues(attributes, EMAIL).get(0);
    }

    List<String> getAttributeValues(List<OpenIDAttribute> attributes, String attributeName) {
        return attributes.stream().filter(a -> attributeName.equals(a.getName())).findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("No " + attributeName + " define for user."))
                .getValues();
    }
}
