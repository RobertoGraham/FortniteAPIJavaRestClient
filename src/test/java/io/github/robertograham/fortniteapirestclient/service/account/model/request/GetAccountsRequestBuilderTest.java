package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
class GetAccountsRequestBuilderTest {

    @Test
    @DisplayName("Test build")
    void build_correctlyBuildGetAccountsRequest() {
        Set<String> accountIds = Stream.of("1", "2", "3")
                .collect(Collectors.toSet());
        String authHeaderValue = "authHeaderValue";

        GetAccountsRequest getAccountsRequest = new GetAccountsRequestBuilder(accountIds)
                .authHeaderValue(authHeaderValue)
                .build();

        assertEquals(getAccountsRequest.getAccountIds(), accountIds);
        assertEquals(getAccountsRequest.getAuthHeaderValue(), authHeaderValue);
    }
}
