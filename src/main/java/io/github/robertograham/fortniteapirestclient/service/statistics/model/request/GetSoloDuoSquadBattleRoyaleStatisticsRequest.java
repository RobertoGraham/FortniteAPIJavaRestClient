package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetSoloDuoSquadBattleRoyaleStatisticsRequest extends Request {

    private final String accountId;
    private final String window;
    private final String authHeaderValue;

    GetSoloDuoSquadBattleRoyaleStatisticsRequest(String accountId, String window, String authHeaderValue) {
        this.accountId = accountId;
        this.window = window;
        this.authHeaderValue = authHeaderValue;
    }

    public static GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder builder() {
        return new GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder();
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
        return "GetSoloDuoSquadBattleRoyaleStatisticsRequest{" +
                "accountId='" + accountId + '\'' +
                ", window='" + window + '\'' +
                ", authHeaderValue='" + authHeaderValue + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof GetSoloDuoSquadBattleRoyaleStatisticsRequest))
            return false;

        GetSoloDuoSquadBattleRoyaleStatisticsRequest getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest = (GetSoloDuoSquadBattleRoyaleStatisticsRequest) object;

        return Objects.equals(accountId, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.accountId) &&
                Objects.equals(window, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.window) &&
                Objects.equals(authHeaderValue, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, window, authHeaderValue);
    }
}
