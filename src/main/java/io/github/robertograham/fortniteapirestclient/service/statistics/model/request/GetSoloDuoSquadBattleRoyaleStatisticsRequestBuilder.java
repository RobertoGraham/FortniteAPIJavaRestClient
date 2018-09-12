package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatWindow;
import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder implements Builder<GetSoloDuoSquadBattleRoyaleStatisticsRequest> {

    private String accountId;
    private StatWindow window;
    private String authorization;

    GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder() {
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder accountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder window(StatWindow window) {
        this.window = window;

        return this;
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder authorization(String authorization) {
        this.authorization = authorization;

        return this;
    }

    @Override
    public GetSoloDuoSquadBattleRoyaleStatisticsRequest build() {
        return new GetSoloDuoSquadBattleRoyaleStatisticsRequest(
                accountId,
                window,
                authorization
        );
    }
}
