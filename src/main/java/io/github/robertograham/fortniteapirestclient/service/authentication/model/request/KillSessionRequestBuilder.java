package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class KillSessionRequestBuilder implements Builder<KillSessionRequest> {

    private String accessToken;
    private String authorization;

    KillSessionRequestBuilder() {
    }

    public KillSessionRequestBuilder accessToken(String accessToken) {
        this.accessToken = accessToken;

        return this;
    }

    public KillSessionRequestBuilder authorization(String authorization) {
        this.authorization = authorization;

        return this;
    }

    @Override
    public KillSessionRequest build() {
        return new KillSessionRequest(
                accessToken,
                authorization
        );
    }
}
