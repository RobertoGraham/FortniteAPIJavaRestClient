package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
class GetExchangeCodeRequestBuilderTest {

    @Test
    @DisplayName("build returns correct GetExchangeCodeRequest")
    void build_correctlyBuildsGetExchangeCodeRequest() {
        String authHeaderValue = "authHeaderValue";

        GetExchangeCodeRequest getExchangeCodeRequest = new GetExchangeCodeRequestBuilder()
                .authHeaderValue(authHeaderValue)
                .build();

        assertEquals(getExchangeCodeRequest.getAuthHeaderValue(), authHeaderValue);
    }
}
