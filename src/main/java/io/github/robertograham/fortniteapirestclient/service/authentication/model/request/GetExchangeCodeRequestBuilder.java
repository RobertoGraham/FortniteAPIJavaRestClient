package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetExchangeCodeRequestBuilder implements Builder<GetExchangeCodeRequest> {

    private String authHeaderValue;

    GetExchangeCodeRequestBuilder() {
    }

    public GetExchangeCodeRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    @Override
    public GetExchangeCodeRequest build() {
        return new GetExchangeCodeRequest(authHeaderValue);
    }
}
