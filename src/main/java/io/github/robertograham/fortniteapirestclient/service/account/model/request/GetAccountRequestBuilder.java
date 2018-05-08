package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetAccountRequestBuilder implements Builder<GetAccountRequest> {

    private String accountName;
    private String authHeaderValue;

    GetAccountRequestBuilder() {
    }

    public GetAccountRequestBuilder accountName(String accountName) {
        this.accountName = accountName;

        return this;
    }

    public GetAccountRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    @Override
    public GetAccountRequest build() {
        return new GetAccountRequest(
                accountName,
                authHeaderValue
        );
    }
}
