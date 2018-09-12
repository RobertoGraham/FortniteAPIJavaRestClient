package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatWindow;
import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest extends Request {

    private final String accountId;
    private final Platform platform;
    private final StatWindow window;
    private final String authorization;

    GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest(String accountId, Platform platform, StatWindow window, String authorization) {
        this.accountId = accountId;
        this.platform = platform;
        this.window = window;
        this.authorization = authorization;
    }

    public static GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder builder() {
        return new GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder();
    }

    public Platform getPlatform() {
        return platform;
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
        return "GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest{" +
                "accountId='" + accountId + '\'' +
                ", platform='" + platform + '\'' +
                ", window='" + window + '\'' +
                ", authorization='" + authorization + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest))
            return false;

        GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest = (GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest) object;

        return Objects.equals(accountId, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.accountId) &&
                Objects.equals(platform, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.platform) &&
                Objects.equals(window, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.window) &&
                Objects.equals(authorization, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.authorization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, platform, window, authorization);
    }
}
