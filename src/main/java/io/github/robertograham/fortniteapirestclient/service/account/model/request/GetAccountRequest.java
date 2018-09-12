package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetAccountRequest extends Request {

    private final String accountName;
    private final String authorization;

    GetAccountRequest(String accountName, String authorization) {
        this.accountName = accountName;
        this.authorization = authorization;
    }

    public static GetAccountRequestBuilder builder() {
        return new GetAccountRequestBuilder();
    }

    public String getAuthorization() {
        return authorization;
    }

    public String getAccountName() {
        return accountName;
    }

    @Override
    public String toString() {
        return "GetAccountRequest{" +
                "accountName='" + accountName + '\'' +
                ", authorization='" + authorization + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        GetAccountRequest getAccountRequest = (GetAccountRequest) object;

        return Objects.equals(accountName, getAccountRequest.accountName) &&
                Objects.equals(authorization, getAccountRequest.authorization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, authorization);
    }
}
