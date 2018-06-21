package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest extends Request {

    private final String accountId;
    private final String platform;
    private final String window;
    private final String authHeaderValue;

    GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest(String accountId, String platform, String window, String authHeaderValue) {
        this.accountId = accountId;
        this.platform = platform;
        this.window = window;
        this.authHeaderValue = authHeaderValue;
    }

    public static GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder builder() {
        return new GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder();
    }

    public String getPlatform() {
        return platform;
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
        return "GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest{" +
                "accountId='" + accountId + '\'' +
                ", platform='" + platform + '\'' +
                ", window='" + window + '\'' +
                ", authHeaderValue='" + authHeaderValue + '\'' +
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
                Objects.equals(authHeaderValue, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, platform, window, authHeaderValue);
    }
}
