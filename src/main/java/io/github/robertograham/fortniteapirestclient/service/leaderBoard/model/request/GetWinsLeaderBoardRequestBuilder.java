package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetWinsLeaderBoardRequestBuilder implements Builder<GetWinsLeaderBoardRequest> {

    private String platform;
    private String partyType;
    private String window;
    private String authHeaderValue;
    private int entryCount;
    private String inAppId;

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

    public GetWinsLeaderBoardRequestBuilder window(String window) {
        this.window = window;

        return this;
    }

    public GetWinsLeaderBoardRequestBuilder authHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;

        return this;
    }

    public GetWinsLeaderBoardRequestBuilder entryCount(int entryCount) {
        this.entryCount = entryCount;

        return this;
    }

    public GetWinsLeaderBoardRequestBuilder inAppId(String inAppId) {
        this.inAppId = inAppId;

        return this;
    }

    @Override
    public GetWinsLeaderBoardRequest build() {
        return new GetWinsLeaderBoardRequest(
                platform,
                partyType,
                window,
                authHeaderValue,
                entryCount,
                inAppId
        );
    }
}
