package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class KillSessionRequestBuilder implements Builder<KillSessionRequest> {

    private String accessToken;
    private String authHeaderValue;

    KillSessionRequestBuilder() {
    }

    public KillSessionRequestBuilder accessToken(String accessToken) {
        this.accessToken = accessToken;

        return this;
    }

    public KillSessionRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    @Override
    public KillSessionRequest build() {
        return new KillSessionRequest(
                accessToken,
                authHeaderValue
        );
    }
}
