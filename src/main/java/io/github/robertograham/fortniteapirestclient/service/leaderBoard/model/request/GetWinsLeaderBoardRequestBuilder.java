package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetWinsLeaderBoardRequestBuilder implements Builder<GetWinsLeaderBoardRequest> {

    private String platform;
    private String partyType;
    private String authHeaderValue;

    GetWinsLeaderBoardRequestBuilder() {
    }

    public GetWinsLeaderBoardRequestBuilder platform(String platform) {
        this.platform = platform;

        return this;
    }

    public GetWinsLeaderBoardRequestBuilder partyType(String partyType) {
        this.partyType = partyType;

        return this;
    }

    public GetWinsLeaderBoardRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    @Override
    public GetWinsLeaderBoardRequest build() {
        return new GetWinsLeaderBoardRequest(platform, partyType, authHeaderValue);
    }
}
