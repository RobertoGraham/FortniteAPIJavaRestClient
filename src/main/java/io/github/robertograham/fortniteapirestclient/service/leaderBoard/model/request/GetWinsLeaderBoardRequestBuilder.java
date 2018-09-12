package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatWindow;
import io.github.robertograham.fortniteapirestclient.util.Builder;

public class GetWinsLeaderBoardRequestBuilder implements Builder<GetWinsLeaderBoardRequest> {

    private Platform platform;
    private PartyType partyType;
    private StatWindow window;
    private String authorization;
    private int entryCount;
    private String inAppId;

    GetWinsLeaderBoardRequestBuilder() {
    }

    public GetWinsLeaderBoardRequestBuilder platform(Platform platform) {
        this.platform = platform;

        return this;
    }

    public GetWinsLeaderBoardRequestBuilder partyType(PartyType partyType) {
        this.partyType = partyType;

        return this;
    }

    public GetWinsLeaderBoardRequestBuilder window(StatWindow window) {
        this.window = window;

        return this;
    }

    public GetWinsLeaderBoardRequestBuilder authorization(String authorization) {
        this.authorization = authorization;

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
                authorization,
                entryCount,
                inAppId
        );
    }
}
