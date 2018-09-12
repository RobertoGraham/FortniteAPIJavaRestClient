package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetExchangeCodeRequestBuilder implements Builder<GetExchangeCodeRequest> {

    private String authorization;

    GetExchangeCodeRequestBuilder() {
    }

    public GetExchangeCodeRequestBuilder authorization(String authorization) {
        this.authorization = authorization;

        return this;
    }

    @Override
    public GetExchangeCodeRequest build() {
        return new GetExchangeCodeRequest(authorization);
    }
}
