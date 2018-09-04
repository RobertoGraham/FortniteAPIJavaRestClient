package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GrantAccessRequest extends Request {

    private final String accountId;
    private final String authHeaderValue;

    GrantAccessRequest(String accountId, String authHeaderValue) {
        this.accountId = accountId;
        this.authHeaderValue = authHeaderValue;
    }

    public static GrantAccessRequestBuilder builder() {
        return new GrantAccessRequestBuilder();
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "GrantAccessRequest{" +
                "accountId='" + accountId + '\'' +
                ", authHeaderValue='" + authHeaderValue + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        GrantAccessRequest grantAccessRequest = (GrantAccessRequest) object;

        return Objects.equals(accountId, grantAccessRequest.accountId) &&
                Objects.equals(authHeaderValue, grantAccessRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, authHeaderValue);
    }
}
