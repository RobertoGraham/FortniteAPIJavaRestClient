package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetBattleRoyaleStatisticsRequestBuilder implements Builder<GetBattleRoyaleStatisticsRequest> {

    private String accountId;
    private String window;
    private String authHeaderValue;

    GetBattleRoyaleStatisticsRequestBuilder() {
    }

    public GetBattleRoyaleStatisticsRequestBuilder accountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public GetBattleRoyaleStatisticsRequestBuilder window(String window) {
        this.window = window;

        return this;
    }

    public GetBattleRoyaleStatisticsRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    @Override
    public GetBattleRoyaleStatisticsRequest build() {
        return new GetBattleRoyaleStatisticsRequest(
                accountId,
                window,
                authHeaderValue
        );
    }
}
