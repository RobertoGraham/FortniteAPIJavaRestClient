package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder implements Builder<GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest> {

    private String platform;
    private String accountId;
    private String window;
    private String authHeaderValue;

    GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder() {
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder platform(String platform) {
        this.platform = platform;

        return this;
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder accountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder window(String window) {
        this.window = window;

        return this;
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    @Override
    public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest build() {
        return new GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest(
                accountId,
                platform,
                window,
                authHeaderValue
        );
    }
}
