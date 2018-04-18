package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import java.util.Set;

public class GetAccountsRequest {

    private final Set<String> accountIds;
    private final String authHeaderValue;

    GetAccountsRequest(Set<String> accountIds, String authHeaderValue) {
        this.accountIds = accountIds;
        this.authHeaderValue = authHeaderValue;
    }

    public static GetAccountsRequestBuilder builder(Set<String> accountIds) {
        return new GetAccountsRequestBuilder(accountIds);
    }

    public Set<String> getAccountIds() {
        return accountIds;
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }
}
