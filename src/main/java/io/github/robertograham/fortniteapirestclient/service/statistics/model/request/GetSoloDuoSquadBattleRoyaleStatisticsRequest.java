package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatWindow;
import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetSoloDuoSquadBattleRoyaleStatisticsRequest extends Request {

    private final String accountId;
    private final StatWindow window;
    private final String authorization;

    GetSoloDuoSquadBattleRoyaleStatisticsRequest(String accountId, StatWindow window, String authorization) {
        this.accountId = accountId;
        this.window = window;
        this.authorization = authorization;
    }

    public static GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder builder() {
        return new GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder();
    }

    public String getAccountId() {
        return accountId;
    }

    public StatWindow getWindow() {
        return window;
    }

    public String getAuthorization() {
        return authorization;
    }

    @Override
    public String toString() {
        return "GetSoloDuoSquadBattleRoyaleStatisticsRequest{" +
                "accountId='" + accountId + '\'' +
                ", window='" + window + '\'' +
                ", authorization='" + authorization + '\'' +
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
                Objects.equals(authorization, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.authorization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, window, authorization);
    }
}
