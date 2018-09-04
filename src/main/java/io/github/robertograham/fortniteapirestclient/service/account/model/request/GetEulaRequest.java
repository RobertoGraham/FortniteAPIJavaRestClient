package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetEulaRequest extends Request {

    private final String accountId;
    private final String authHeaderValue;

    GetEulaRequest(String accountId, String authHeaderValue) {
        this.accountId = accountId;
        this.authHeaderValue = authHeaderValue;
    }

    public static GetEulaRequestBuilder builder() {
        return new GetEulaRequestBuilder();
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "GetEulaRequest{" +
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

        GetEulaRequest getEulaRequest = (GetEulaRequest) object;

        return Objects.equals(accountId, getEulaRequest.accountId) &&
                Objects.equals(authHeaderValue, getEulaRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, authHeaderValue);
    }
}
