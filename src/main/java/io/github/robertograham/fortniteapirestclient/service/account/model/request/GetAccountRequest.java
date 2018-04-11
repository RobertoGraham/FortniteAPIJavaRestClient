package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.IBuilder;

public class GetAccountRequest {

    private String accountName;
    private String authHeaderValue;

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public void setAuthHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public static class Builder implements IBuilder<GetAccountRequest> {

        private GetAccountRequest getAccountRequest;

        private Builder() {
            getAccountRequest = new GetAccountRequest();
        }

        public static Builder newInstance() {
            return new GetAccountRequest.Builder();
        }

        public Builder accountName(String accountName) {
            getAccountRequest.setAccountName(accountName);

            return this;
        }

        public Builder authHeaderValue(String authHeaderValue) {
            getAccountRequest.setAuthHeaderValue(authHeaderValue);

            return this;
        }

        @Override
        public GetAccountRequest build() {
            return getAccountRequest;
        }
    }
}
