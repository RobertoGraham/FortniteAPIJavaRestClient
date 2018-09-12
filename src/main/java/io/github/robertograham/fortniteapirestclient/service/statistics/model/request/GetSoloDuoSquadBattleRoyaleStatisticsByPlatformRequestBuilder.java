package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatWindow;
import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder implements Builder<GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest> {

    private Platform platform;
    private String accountId;
    private StatWindow window;
    private String authorization;

    GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder() {
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder platform(Platform platform) {
        this.platform = platform;

        return this;
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder accountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder window(StatWindow window) {
        this.window = window;

        return this;
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequestBuilder authorization(String authorization) {
        this.authorization = authorization;

        return this;
    }

    @Override
    public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest build() {
        return new GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest(
                accountId,
                platform,
                window,
                authorization
        );
    }
}
