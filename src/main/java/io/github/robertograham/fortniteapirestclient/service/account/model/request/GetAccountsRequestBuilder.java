package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

import java.util.Objects;
import java.util.Set;

public class GetAccountsRequestBuilder implements Builder<GetAccountsRequest> {

    private Set<String> accountIds;
    private String authorization;

    GetAccountsRequestBuilder(Set<String> accountIds) {
        Objects.requireNonNull(accountIds, "Account id set cannot be null");

        if (accountIds.size() < 1)
            throw new IllegalArgumentException("No account ids provided");

        this.accountIds = accountIds;
    }

    public GetAccountsRequestBuilder authorization(String authorization) {
        this.authorization = authorization;

        return this;
    }

    @Override
    public GetAccountsRequest build() {
        return new GetAccountsRequest(
                accountIds,
                authorization
        );
    }
}
