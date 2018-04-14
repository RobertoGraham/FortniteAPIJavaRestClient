package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import java.util.Objects;

public class GetAccountRequest {

    private final String accountName;
    private final String authHeaderValue;

    GetAccountRequest(String accountName, String authHeaderValue) {
        this.accountName = accountName;
        this.authHeaderValue = authHeaderValue;
    }

    public static GetAccountRequestBuilder builder() {
        return new GetAccountRequestBuilder();
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public String getAccountName() {
        return accountName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        GetAccountRequest getAccountRequest = (GetAccountRequest) object;

        return Objects.equals(accountName, getAccountRequest.accountName) &&
                Objects.equals(authHeaderValue, getAccountRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, authHeaderValue);
    }
}
