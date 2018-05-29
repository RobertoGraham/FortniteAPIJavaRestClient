package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

public class GetWinsLeaderBoardRequest {
    private final String platform;
    private final String partyType;
    private final String window;
    private final int entryCount;

    private final String authHeaderValue;

    GetWinsLeaderBoardRequest(String platform, String partyType, String window, String authHeaderValue, int entryCount) {
        this.platform = platform;
        this.partyType = partyType;
        this.window = window;
        this.authHeaderValue = authHeaderValue;
        this.entryCount = entryCount;
    }

    public static GetWinsLeaderBoardRequestBuilder builder() {
        return new GetWinsLeaderBoardRequestBuilder();
    }

    public String getPlatform() {
        return platform;
    }

    public String getPartyType() {
        return partyType;
    }

    public String getWindow() {
        return window;
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public int getEntryCount() {
        return entryCount;
    }
}
