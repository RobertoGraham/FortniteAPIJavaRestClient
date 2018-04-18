package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

public class GetWinsLeaderBoardRequest {
    private final String platform;
    private final String partyType;
    private final String authHeaderValue;

    public static GetWinsLeaderBoardRequestBuilder builder() {
        return new GetWinsLeaderBoardRequestBuilder();
    }

    GetWinsLeaderBoardRequest(String platform, String partyType, String authHeaderValue) {
        this.platform = platform;
        this.partyType = partyType;
        this.authHeaderValue = authHeaderValue;
    }

    public String getPlatform() {
        return platform;
    }

    public String getPartyType() {
        return partyType;
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }
}
