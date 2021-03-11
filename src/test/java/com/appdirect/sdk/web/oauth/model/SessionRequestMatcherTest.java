package com.appdirect.sdk.web.oauth.model;

import static com.appdirect.sdk.utils.ConstantUtils.AD_AUTHORIZATION_HEADER;
import static com.appdirect.sdk.utils.ConstantUtils.AUTHORIZATION;
import static com.appdirect.sdk.utils.ConstantUtils.BASIC;
import static com.appdirect.sdk.utils.ConstantUtils.BEARER;
import static com.appdirect.sdk.utils.ConstantUtils.DOMAIN_ASSOCIATION_PATH;
import static com.appdirect.sdk.utils.ConstantUtils.INTEGRATION_PATH;
import static com.appdirect.sdk.utils.ConstantUtils.MIGRATION_PATH;
import static com.appdirect.sdk.utils.ConstantUtils.RESTRICTIONS_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import org.mockito.InjectMocks;
import org.springframework.mock.web.MockHttpServletRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SessionRequestMatcherTest {

    @InjectMocks
    private SessionRequestMatcher sessionRequestMatcher;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        initMocks(this);
    }

    @Test
    public void authorizationRequestMatcher_validADHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AD_AUTHORIZATION_HEADER, BEARER);;
        assertThat(sessionRequestMatcher.matches(request)).isFalse();
    }

    @Test
    public void matches_basicAuthorization_valid_event_api() {
        MockHttpServletRequest request = createRequest(INTEGRATION_PATH);
        assertThat(sessionRequestMatcher.matches(request)).isTrue();
    }

    @Test
    public void matches_basicAuthorization_valid_domain_api() {
        MockHttpServletRequest request = createRequest(DOMAIN_ASSOCIATION_PATH);
        assertThat(sessionRequestMatcher.matches(request)).isTrue();
    }

    @Test
    public void matches_basicAuthorization_valid_migration_api() {
        MockHttpServletRequest request = createRequest(MIGRATION_PATH);
        assertThat(sessionRequestMatcher.matches(request)).isTrue();
    }

    @Test
    public void matches_basicAuthorization_valid_restriction_api() {
        MockHttpServletRequest request = createRequest(RESTRICTIONS_PATH);
        assertThat(sessionRequestMatcher.matches(request)).isTrue();
    }

    private MockHttpServletRequest createRequest(String path) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, BASIC);
        request.setServletPath(path);
        return request;
    }
}
