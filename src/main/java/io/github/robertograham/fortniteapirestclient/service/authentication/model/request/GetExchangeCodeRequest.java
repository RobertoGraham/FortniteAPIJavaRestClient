package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.IBuilder;

public class GetExchangeCodeRequest {

    private String authHeaderValue;

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public void setAuthHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;
    }

    public static class Builder implements IBuilder<GetExchangeCodeRequest> {

        private GetExchangeCodeRequest getExchangeCodeRequest;

        private Builder() {
            getExchangeCodeRequest = new GetExchangeCodeRequest();
        }

        public static Builder newInstance() {
            return new GetExchangeCodeRequest.Builder();
        }

        public Builder authHeaderValue(String authHeaderValue) {
            getExchangeCodeRequest.setAuthHeaderValue(authHeaderValue);

            return this;
        }

        @Override
        public GetExchangeCodeRequest build() {
            return getExchangeCodeRequest;
        }
    }
}
