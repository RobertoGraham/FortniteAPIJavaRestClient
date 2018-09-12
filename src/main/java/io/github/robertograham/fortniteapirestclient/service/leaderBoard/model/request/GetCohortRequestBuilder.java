package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetCohortRequestBuilder implements Builder<GetCohortRequest> {

    private String inAppId;
    private Platform platform;
    private PartyType partyType;
    private String authorization;

    GetCohortRequestBuilder() {
    }

    public GetCohortRequestBuilder inAppId(String inAppId) {
        this.inAppId = inAppId;

        return this;
    }

    public GetCohortRequestBuilder platform(Platform platform) {
        this.platform = platform;

        return this;
    }

    public GetCohortRequestBuilder partyType(PartyType partyType) {
        this.partyType = partyType;

        return this;
    }

    public GetCohortRequestBuilder authorization(String authorization) {
        this.authorization = authorization;

        return this;
    }

    @Override
    public GetCohortRequest build() {
        return new GetCohortRequest(
                inAppId,
                platform,
                partyType,
                authorization
        );
    }
}
