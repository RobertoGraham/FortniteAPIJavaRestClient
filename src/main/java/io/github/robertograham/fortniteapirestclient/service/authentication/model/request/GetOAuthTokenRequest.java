package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;


import org.apache.http.NameValuePair;

import java.util.Arrays;
import java.util.Objects;

public class GetOAuthTokenRequest {

    private final String grantType;
    private final String authHeaderValue;
    private final NameValuePair[] additionalFormEntries;

    GetOAuthTokenRequest(String grantType, String authHeaderValue, NameValuePair[] additionalFormEntries) {
        this.grantType = grantType;
        this.authHeaderValue = authHeaderValue;
        this.additionalFormEntries = additionalFormEntries;
    }

    public static GetOAuthTokenRequestBuilder builder() {
        return new GetOAuthTokenRequestBuilder();
    }

    public String getGrantType() {
        return grantType;
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public NameValuePair[] getAdditionalFormEntries() {
        return additionalFormEntries;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        GetOAuthTokenRequest getOAuthTokenRequest = (GetOAuthTokenRequest) object;

        return Objects.equals(grantType, getOAuthTokenRequest.grantType) &&
                Objects.equals(authHeaderValue, getOAuthTokenRequest.authHeaderValue) &&
                Arrays.equals(additionalFormEntries, getOAuthTokenRequest.additionalFormEntries);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(grantType, authHeaderValue);
        result = 31 * result + Arrays.hashCode(additionalFormEntries);
        return result;
    }
}
