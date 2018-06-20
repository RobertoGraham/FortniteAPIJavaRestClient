package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnitPlatform.class)
class GetOAuthTokenRequestBuilderTest {

    @Test
    @DisplayName("build returns correct GetOAuthTokenRequest")
    void build_correctlyBuildsGetOAuthTokenRequest() {
        String grantType = "grantType";
        String authHeaderValue = "authHeaderValue";
        NameValuePair[] additionalFormEntries = new NameValuePair[]{new BasicNameValuePair("", "")};

        GetOAuthTokenRequest getOAuthTokenRequest = new GetOAuthTokenRequestBuilder()
                .grantType(grantType)
                .authHeaderValue(authHeaderValue)
                .additionalFormEntries(additionalFormEntries)
                .build();

        assertEquals(getOAuthTokenRequest.getGrantType(), grantType);
        assertEquals(getOAuthTokenRequest.getAuthHeaderValue(), authHeaderValue);
        assertTrue(Arrays.equals(getOAuthTokenRequest.getAdditionalFormEntries(), additionalFormEntries));
    }
}
