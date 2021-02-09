package com.appdirect.sdk.web.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import com.appdirect.sdk.appmarket.OAuth2CredentialsSupplier;

public class OAuth2ClientDetailsServiceTest {
    private static final String OUTBOUND_CLIENT_ID = "my-outbound-client-id";
    private static final String OUTBOUND_CLIENT_SECRET = "my-outbound-client-secret";
    private static final String OUTBOUND_SCOPE = "offline openid";
    public static final String SCOPE_DELIMITER = " ";
    private static final String OUTBOUND_TOKEN_URI = "https://tokenuri";
    private static final String APPLICATION_ID = "applicationUuid";


    private OAuth2ClientDetailsService  oAuth2ClientDetailsService;
    private OAuth2CredentialsSupplier oAuth2CredentialsSupplier;



    @Before
    public void setup() throws Exception {
        oAuth2CredentialsSupplier = mock(OAuth2CredentialsSupplier.class);
        oAuth2ClientDetailsService = new OAuth2ClientDetailsServiceImpl(oAuth2CredentialsSupplier);
    }

    @Test
    public void loadClientCredentailsByApplicationUuidKey_buildsOAuth2CredentialsDetails_passesKeyToOAuth2CredentialsSupplier() throws Exception {
        when(oAuth2CredentialsSupplier.getOAuth2ResourceDetails(APPLICATION_ID)).thenReturn(createOAuth2ProtectedResourceDetails());
        OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails = oAuth2ClientDetailsService.getOAuth2ProtectedResourceDetails(APPLICATION_ID);

        assertThat(oAuth2ProtectedResourceDetails.getClientId()).isEqualTo(OUTBOUND_CLIENT_ID);
        assertThat(oAuth2ProtectedResourceDetails.getClientSecret()).isEqualTo(OUTBOUND_CLIENT_SECRET);
    }

    private OAuth2ProtectedResourceDetails createOAuth2ProtectedResourceDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId(OUTBOUND_CLIENT_ID);
        details.setClientId(OUTBOUND_CLIENT_ID);
        details.setClientSecret(OUTBOUND_CLIENT_SECRET);
        details.setAccessTokenUri(OUTBOUND_TOKEN_URI);
        details.setScope(Arrays.asList(OUTBOUND_SCOPE.split(SCOPE_DELIMITER)));
        return details;
    }

}
