package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetEulaRequestBuilder implements Builder<GetEulaRequest> {

    private String accountId;
    private String authHeaderValue;

    GetEulaRequestBuilder() {
    }

    public GetEulaRequestBuilder accountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public GetEulaRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    @Override
    public GetEulaRequest build() {
        return new GetEulaRequest(
                accountId,
                authHeaderValue
        );
    }
}
