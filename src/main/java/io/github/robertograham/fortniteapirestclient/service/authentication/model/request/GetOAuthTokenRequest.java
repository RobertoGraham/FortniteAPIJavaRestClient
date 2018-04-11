package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;


import io.github.robertograham.fortniteapirestclient.util.IBuilder;
import org.apache.http.NameValuePair;

public class GetOAuthTokenRequest {

    private String grantType;
    private String authHeaderValue;
    private NameValuePair[] additionalFormEntries;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public void setAuthHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;
    }

    public NameValuePair[] getAdditionalFormEntries() {
        return additionalFormEntries;
    }

    public void setAdditionalFormEntries(NameValuePair[] additionalFormEntries) {
        this.additionalFormEntries = additionalFormEntries;
    }

    public static class Builder implements IBuilder<GetOAuthTokenRequest> {

        private GetOAuthTokenRequest getOAuthTokenRequest;

        private Builder() {
            getOAuthTokenRequest = new GetOAuthTokenRequest();
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder grantType(String grantType) {
            getOAuthTokenRequest.setGrantType(grantType);

            return this;
        }

        public Builder authHeaderValue(String authHeaderValue) {
            getOAuthTokenRequest.setAuthHeaderValue(authHeaderValue);

            return this;
        }

        public Builder additionalFormEntries(NameValuePair[] additionalFormEntries) {
            getOAuthTokenRequest.setAdditionalFormEntries(additionalFormEntries);

            return this;
        }

        @Override
        public GetOAuthTokenRequest build() {
            return getOAuthTokenRequest;
        }
    }
}
