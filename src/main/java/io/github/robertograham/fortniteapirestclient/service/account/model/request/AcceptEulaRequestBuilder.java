package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class AcceptEulaRequestBuilder implements Builder<AcceptEulaRequest> {

    private String accountId;
    private String authHeaderValue;
    private int version;

    AcceptEulaRequestBuilder() {
    }

    public AcceptEulaRequestBuilder accountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public AcceptEulaRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    public AcceptEulaRequestBuilder version(int version) {
        this.version = version;

        return this;
    }

    @Override
    public AcceptEulaRequest build() {
        return new AcceptEulaRequest(
                accountId,
                version,
                authHeaderValue
        );
    }
}
