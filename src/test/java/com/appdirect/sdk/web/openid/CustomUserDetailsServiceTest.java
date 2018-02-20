package com.appdirect.sdk.web.openid;

import com.appdirect.sdk.utils.RoleMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CustomUserDetailsServiceTest {

    private static final String USERNAME = "admin";
    private static final String EMAIL = "email@test.com";
    private static final String USER_ROLE = "USER";
    private static final List<String> USER_ROLES = Arrays.asList(USER_ROLE);
    private static final Set<String> MAPPED_ROLES = new HashSet<>(Arrays.asList("viewer"));
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
        doReturn(MAPPED_ROLES).when(roleMapper).roleMap(any());
        doReturn(USER_ROLES).when(customUserDetailsService).getGrantedAuthorities(any());
        doReturn(USERNAME).when(customUserDetailsService).getFullName(any());
        doReturn(EMAIL).when(customUserDetailsService).getEmail(any());

        // When
        UserDetails userDetails = customUserDetailsService.loadUserDetails(token);

        // Then
        assertThat(userDetails.getUsername()).isEqualTo(USERNAME);
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
        assertThat(userDetails.getAuthorities()).hasSize(1);
    }

    @Test
    public void testGetGrantedAuthorities() {
        // Given
        List<OpenIDAttribute> attributes = new ArrayList<>();
        doReturn(Arrays.asList(USER_ROLE)).when(customUserDetailsService).getAttributeValues(attributes, ROLES_ATTRIBUTE);

        // When
        List<String> grantedAuthorities = customUserDetailsService.getGrantedAuthorities(attributes);

        // Then
        assertThat(grantedAuthorities.get(0)).isEqualTo(USER_ROLE);
    }

    @Test
    public void testGetFullName() {
        // Given
        List<OpenIDAttribute> attributes = new ArrayList<>();
        doReturn(Arrays.asList(USERNAME)).when(customUserDetailsService).getAttributeValues(attributes, FULLNAME_ATTRIBUTE);

        // When
        String fullName = customUserDetailsService.getFullName(attributes);

        // Then
        assertThat(fullName).isEqualTo(USERNAME);
    }

    @Test
    public void testGetEmail() {
        // Given
        List<OpenIDAttribute> attributes = new ArrayList<>();
        doReturn(Arrays.asList(EMAIL)).when(customUserDetailsService).getAttributeValues(attributes, EMAIL_ATTRIBUTE);

        // When
        String email = customUserDetailsService.getEmail(attributes);

        // Then
        assertThat(email).isEqualTo(EMAIL);
    }

    @Test
    public void testGetAttributeValues() {
        // Given
        List<String> values = Arrays.asList(EMAIL);
        OpenIDAttribute openIDAttribute = mock(OpenIDAttribute.class);
        when(openIDAttribute.getName()).thenReturn(FULLNAME_ATTRIBUTE);
        when(openIDAttribute.getValues()).thenReturn(values);
        List<OpenIDAttribute> attributes = Arrays.asList(openIDAttribute);

        // When
        List<String> attributeValues = customUserDetailsService.getAttributeValues(attributes, FULLNAME_ATTRIBUTE);

        // Then
        assertThat(attributeValues).hasSize(1);
        assertThat(attributeValues.get(0)).isEqualTo(EMAIL);
    }
}
