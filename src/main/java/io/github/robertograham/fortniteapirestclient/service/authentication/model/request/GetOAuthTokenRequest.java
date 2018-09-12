package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;


import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Map;
import java.util.Objects;

public class GetOAuthTokenRequest extends Request {

    private final String grantType;
    private final String authorization;
    private final Map<String, String> additionalFormEntries;

    GetOAuthTokenRequest(String grantType, String authorization, Map<String, String> additionalFormEntries) {
        this.grantType = grantType;
        this.authorization = authorization;
        this.additionalFormEntries = additionalFormEntries;
    }

    public static GetOAuthTokenRequestBuilder builder() {
        return new GetOAuthTokenRequestBuilder();
    }

    public String getGrantType() {
        return grantType;
    }

    public String getAuthorization() {
        return authorization;
    }

    public Map<String, String> getAdditionalFormEntries() {
        return additionalFormEntries;
    }

    @Override
    public String toString() {
        return "GetOAuthTokenRequest{" +
                "grantType='" + grantType + '\'' +
                ", authorization='" + authorization + '\'' +
                ", additionalFormEntries=" + additionalFormEntries +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof GetOAuthTokenRequest))
            return false;

        GetOAuthTokenRequest getOAuthTokenRequest = (GetOAuthTokenRequest) object;

        return Objects.equals(grantType, getOAuthTokenRequest.grantType) &&
                Objects.equals(authorization, getOAuthTokenRequest.authorization) &&
                Objects.equals(additionalFormEntries, getOAuthTokenRequest.additionalFormEntries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grantType, authorization, additionalFormEntries);
    }
}
