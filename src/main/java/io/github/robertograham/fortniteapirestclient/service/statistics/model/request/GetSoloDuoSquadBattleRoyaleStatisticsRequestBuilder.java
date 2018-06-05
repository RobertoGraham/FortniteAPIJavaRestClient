package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder implements Builder<GetSoloDuoSquadBattleRoyaleStatisticsRequest> {

    private String accountId;
    private String window;
    private String authHeaderValue;

    GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder() {
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder accountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder window(String window) {
        this.window = window;

        return this;
    }

    public GetSoloDuoSquadBattleRoyaleStatisticsRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    @Override
    public GetSoloDuoSquadBattleRoyaleStatisticsRequest build() {
        return new GetSoloDuoSquadBattleRoyaleStatisticsRequest(
                accountId,
                window,
                authHeaderValue
        );
    }
}
