package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;
import java.util.Set;

public class GetAccountsRequest extends Request {

    private final Set<String> accountIds;
    private final String authorization;

    GetAccountsRequest(Set<String> accountIds, String authorization) {
        this.accountIds = accountIds;
        this.authorization = authorization;
    }

    public static GetAccountsRequestBuilder builder(Set<String> accountIds) {
        return new GetAccountsRequestBuilder(accountIds);
    }

    public Set<String> getAccountIds() {
        return accountIds;
    }

    public String getAuthorization() {
        return authorization;
    }

    @Override
    public String toString() {
        return "GetAccountsRequest{" +
                "accountIds=" + accountIds +
                ", authorization='" + authorization + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof GetAccountsRequest))
            return false;

        GetAccountsRequest getAccountsRequest = (GetAccountsRequest) object;

        return Objects.equals(accountIds, getAccountsRequest.accountIds) &&
                Objects.equals(authorization, getAccountsRequest.authorization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountIds, authorization);
    }
}
