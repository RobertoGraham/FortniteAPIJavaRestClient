package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;
import java.util.Set;

public class GetAccountsRequest extends Request {

    private final Set<String> accountIds;
    private final String authHeaderValue;

    GetAccountsRequest(Set<String> accountIds, String authHeaderValue) {
        this.accountIds = accountIds;
        this.authHeaderValue = authHeaderValue;
    }

    public static GetAccountsRequestBuilder builder(Set<String> accountIds) {
        return new GetAccountsRequestBuilder(accountIds);
    }

    public Set<String> getAccountIds() {
        return accountIds;
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    @Override
    public String toString() {
        return "GetAccountsRequest{" +
                "accountIds=" + accountIds +
                ", authHeaderValue='" + authHeaderValue + '\'' +
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
                Objects.equals(authHeaderValue, getAccountsRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountIds, authHeaderValue);
    }
}
