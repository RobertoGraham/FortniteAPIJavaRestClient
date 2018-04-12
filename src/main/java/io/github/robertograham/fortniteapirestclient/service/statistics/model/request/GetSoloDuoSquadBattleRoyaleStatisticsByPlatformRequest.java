package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import java.util.Objects;

public class GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest {

    private final String accountId;
    private final String platform;
    private final String authHeaderValue;

    GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest(String accountId, String platform, String authHeaderValue) {
        this.accountId = accountId;
        this.platform = platform;
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

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest = (GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest) object;

        return Objects.equals(accountId, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.accountId) &&
                Objects.equals(platform, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.platform) &&
                Objects.equals(authHeaderValue, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, platform, authHeaderValue);
    }
}
