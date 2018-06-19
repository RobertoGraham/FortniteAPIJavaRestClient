package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetBattleRoyaleStatisticsRequest extends Request {

    private final String accountId;
    private final String window;
    private final String authHeaderValue;

    GetBattleRoyaleStatisticsRequest(String accountId, String window, String authHeaderValue) {
        this.accountId = accountId;
        this.window = window;
        this.authHeaderValue = authHeaderValue;
    }

    public static GetBattleRoyaleStatisticsRequestBuilder builder() {
        return new GetBattleRoyaleStatisticsRequestBuilder();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getWindow() {
        return window;
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    @Override
    public String toString() {
        return "GetBattleRoyaleStatisticsRequest{" +
                "accountId='" + accountId + '\'' +
                ", window='" + window + '\'' +
                ", authHeaderValue='" + authHeaderValue + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof GetBattleRoyaleStatisticsRequest))
            return false;

        GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest = (GetBattleRoyaleStatisticsRequest) object;

        return Objects.equals(accountId, getBattleRoyaleStatisticsRequest.accountId) &&
                Objects.equals(window, getBattleRoyaleStatisticsRequest.window) &&
                Objects.equals(authHeaderValue, getBattleRoyaleStatisticsRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, window, authHeaderValue);
    }
}
