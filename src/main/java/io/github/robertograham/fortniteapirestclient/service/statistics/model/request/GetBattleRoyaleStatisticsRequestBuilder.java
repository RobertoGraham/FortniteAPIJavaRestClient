package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatWindow;
import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetBattleRoyaleStatisticsRequestBuilder implements Builder<GetBattleRoyaleStatisticsRequest> {

    private String accountId;
    private StatWindow window;
    private String authorization;

    GetBattleRoyaleStatisticsRequestBuilder() {
    }

    public GetBattleRoyaleStatisticsRequestBuilder accountId(String accountId) {
        this.accountId = accountId;

        return this;
    }

    public GetBattleRoyaleStatisticsRequestBuilder window(StatWindow window) {
        this.window = window;

        return this;
    }

    public GetBattleRoyaleStatisticsRequestBuilder authorization(String authorization) {
        this.authorization = authorization;

        return this;
    }

    @Override
    public GetBattleRoyaleStatisticsRequest build() {
        return new GetBattleRoyaleStatisticsRequest(
                accountId,
                window,
                authorization
        );
    }
}
