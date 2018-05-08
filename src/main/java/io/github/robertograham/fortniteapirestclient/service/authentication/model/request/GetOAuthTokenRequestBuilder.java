package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;
import org.apache.http.NameValuePair;

import java.util.Objects;

public class GetOAuthTokenRequestBuilder implements Builder<GetOAuthTokenRequest> {

    private String grantType;
    private String authHeaderValue;
    private NameValuePair[] additionalFormEntries;

    GetOAuthTokenRequestBuilder() {
    }

    public GetOAuthTokenRequestBuilder grantType(String grantType) {
        this.grantType = grantType;

        return this;
    }

    public GetOAuthTokenRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    public GetOAuthTokenRequestBuilder additionalFormEntries(NameValuePair[] additionalFormEntries) {
        Objects.requireNonNull(additionalFormEntries, "Additional form entries cannot be null");

        for (NameValuePair nameValuePair : additionalFormEntries)
            Objects.requireNonNull(nameValuePair, "Additional form entry cannot be null");

        this.additionalFormEntries = additionalFormEntries;

        return this;
    }

    @Override
    public GetOAuthTokenRequest build() {
        if (additionalFormEntries == null)
            additionalFormEntries = new NameValuePair[0];

        return new GetOAuthTokenRequest(
                grantType,
                authHeaderValue,
                additionalFormEntries
        );
    }
}
