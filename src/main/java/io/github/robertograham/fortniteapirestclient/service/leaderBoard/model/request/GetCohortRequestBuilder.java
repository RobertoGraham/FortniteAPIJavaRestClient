package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetCohortRequestBuilder implements Builder<GetCohortRequest> {

    private String inAppId;
    private String platform;
    private String partyType;
    private String authHeaderValue;

    GetCohortRequestBuilder() {
    }

    public GetCohortRequestBuilder inAppId(String inAppId) {
        this.inAppId = inAppId;

        return this;
    }

    public GetCohortRequestBuilder platform(String platform) {
        this.platform = platform;

        return this;
    }

    public GetCohortRequestBuilder partyType(String partyType) {
        this.partyType = partyType;

        return this;
    }

    public GetCohortRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    @Override
    public GetCohortRequest build() {
        return new GetCohortRequest(
                inAppId,
                platform,
                partyType,
                authHeaderValue
        );
    }
}
