package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.IBuilder;

public class KillSessionRequest {

    private String accessToken;
    private String authHeaderValue;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public void setAuthHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;
    }

    public static class Builder implements IBuilder<KillSessionRequest> {

        private KillSessionRequest killSessionRequest;

        private Builder() {
            killSessionRequest = new KillSessionRequest();
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder accessToken(String accessToken) {
            killSessionRequest.setAccessToken(accessToken);

            return this;
        }

        public Builder authHeaderValue(String authHeaderValue) {
            killSessionRequest.setAuthHeaderValue(authHeaderValue);

            return this;
        }

        @Override
        public KillSessionRequest build() {
            return killSessionRequest;
        }
    }
}
