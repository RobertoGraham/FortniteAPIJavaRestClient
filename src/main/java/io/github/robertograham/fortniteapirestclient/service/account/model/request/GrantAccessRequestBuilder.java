package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GrantAccessRequestBuilder implements Builder<GrantAccessRequest> {

    private String accountId;
    private String authHeaderValue;

    GrantAccessRequestBuilder() {
    }

    public GrantAccessRequestBuilder accountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public GrantAccessRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    @Override
    public GrantAccessRequest build() {
        return new GrantAccessRequest(
                accountId,
                authHeaderValue
        );
    }
}
