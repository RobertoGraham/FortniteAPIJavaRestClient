package io.github.robertograham.fortniteapirestclient.service.account.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class AcceptEulaRequest extends Request {

    private final String accountId;
    private final int version;
    private final String authHeaderValue;

    AcceptEulaRequest(String accountId, int version, String authHeaderValue) {
        this.accountId = accountId;
        this.version = version;
        this.authHeaderValue = authHeaderValue;
    }

    public static AcceptEulaRequestBuilder builder() {
        return new AcceptEulaRequestBuilder();
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public int getVersion() {
        return version;
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "AcceptEulaRequest{" +
                "accountId='" + accountId + '\'' +
                ", version='" + version + '\'' +
                ", authHeaderValue='" + authHeaderValue + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof AcceptEulaRequest))
            return false;

        AcceptEulaRequest that = (AcceptEulaRequest) object;

        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(version, that.version) &&
                Objects.equals(authHeaderValue, that.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, version, authHeaderValue);
    }

}
