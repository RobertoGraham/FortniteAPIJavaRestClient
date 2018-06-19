package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
class GetAccountRequestBuilderTest {

    @Test
    @DisplayName("build returns correct GetAccountRequest")
    void build_correctlyBuildsGetAccountRequest() {
        String accountName = "accountName";
        String authHeaderValue = "authHeaderValue";

        GetAccountRequest getAccountRequest = new GetAccountRequestBuilder()
                .accountName(accountName)
                .authHeaderValue(authHeaderValue)
                .build();

        assertEquals(getAccountRequest.getAccountName(), accountName);
        assertEquals(getAccountRequest.getAuthHeaderValue(), authHeaderValue);
    }
}
