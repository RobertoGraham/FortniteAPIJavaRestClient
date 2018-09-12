package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetOAuthTokenRequestBuilder implements Builder<GetOAuthTokenRequest> {

    private String grantType;
    private String authorization;
    private Map<String, String> additionalFormEntries;

    GetOAuthTokenRequestBuilder() {
    }

    public GetOAuthTokenRequestBuilder grantType(String grantType) {
        this.grantType = grantType;

        return this;
    }

    public GetOAuthTokenRequestBuilder authorization(String authorization) {
        this.authorization = authorization;

        return this;
    }

    public GetOAuthTokenRequestBuilder additionalFormEntries(Map<String, String> additionalFormEntries) {
        Objects.requireNonNull(additionalFormEntries, "Additional form entries cannot be null");

        this.additionalFormEntries = additionalFormEntries;

        return this;
    }

    @Override
    public GetOAuthTokenRequest build() {
        if (additionalFormEntries == null)
            additionalFormEntries(new HashMap<>());

        return new GetOAuthTokenRequest(
                grantType,
                authorization,
                additionalFormEntries
        );
    }
}
