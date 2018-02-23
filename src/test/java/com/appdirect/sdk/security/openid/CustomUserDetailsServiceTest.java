package com.appdirect.sdk.security.openid;

import com.appdirect.sdk.security.openid.service.CustomUserDetailsService;
import com.appdirect.sdk.security.utils.RoleMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CustomUserDetailsServiceTest {

    private static final String ROLES_ATTRIBUTE = "roles";
    private static final String FULLNAME_ATTRIBUTE = "fullname";
    private static final String EMAIL_ATTRIBUTE = "email";

    @Mock
    private RoleMapper roleMapper;

    @Spy
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @After
    public void tearDown() {
        customUserDetailsService = null;
    }

    @Test
    public void testLoadUserDetails() {
        // Given
        OpenIDAuthenticationToken token = mock(OpenIDAuthenticationToken.class);
        doReturn(new HashSet<>(singletonList("viewer"))).when(roleMapper).roleMap(any());
        when(token.getAttributes()).thenReturn(asList(
                anOpenIDAttributeWithValues(ROLES_ATTRIBUTE, singletonList("USER")),
                anOpenIDAttributeWithValues(FULLNAME_ATTRIBUTE, singletonList("admin")),
                anOpenIDAttributeWithValues(EMAIL_ATTRIBUTE, singletonList("email@test.com"))
                ));

        // When
        UserDetails userDetails = customUserDetailsService.loadUserDetails(token);

        // Then
        assertThat(userDetails.getUsername()).isEqualTo("admin");
        assertThat(userDetails.isAccountNonExpired()).isTrue();
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
        assertThat(userDetails.isAccountNonLocked()).isTrue();
        assertThat(userDetails.getAuthorities()).hasSize(1).extracting(GrantedAuthority::getAuthority).containsOnly("ROLE_viewer");
    }

    private OpenIDAttribute anOpenIDAttributeWithValues(String attributeName, List<String> values) {
        return new OpenIDAttribute(attributeName, "", values);
    }
}
