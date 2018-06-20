package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
class KillSessionRequestBuilderTest {

    @Test
    @DisplayName("build returns correct KillSessionRequest")
    void build_correctlyBuildsKillSessionRequest() {
        String accessToken = "accessToken";
        String authHeaderValue = "authHeaderValue";

        KillSessionRequest killSessionRequest = new KillSessionRequestBuilder()
                .accessToken(accessToken)
                .authHeaderValue(authHeaderValue)
                .build();

        assertEquals(killSessionRequest.getAccessToken(), accessToken);
        assertEquals(killSessionRequest.getAuthHeaderValue(), authHeaderValue);
    }
}
