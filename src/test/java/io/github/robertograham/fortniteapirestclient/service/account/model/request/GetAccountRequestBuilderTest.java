package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetAccountRequestBuilderTest {

    @Test
    public void build_correctlyBuildsGetAccountRequest() {
        String accountName = "accountName";
        String authHeaderValue = "authHeaderValue";

        GetAccountRequest getAccountRequest = new GetAccountRequestBuilder()
                .accountName(accountName)
                .authHeaderValue(authHeaderValue)
                .build();

        assertThat(getAccountRequest.getAccountName(), equalTo(accountName));
        assertThat(getAccountRequest.getAuthHeaderValue(), equalTo(authHeaderValue));
    }
}
