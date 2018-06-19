package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetWinsLeaderBoardRequest extends Request {
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

    @Override
    public String toString() {
        return "GetWinsLeaderBoardRequest{" +
                "platform='" + platform + '\'' +
                ", partyType='" + partyType + '\'' +
                ", window='" + window + '\'' +
                ", entryCount=" + entryCount +
                ", authHeaderValue='" + authHeaderValue + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof GetWinsLeaderBoardRequest))
            return false;

        GetWinsLeaderBoardRequest getWinsLeaderBoardRequest = (GetWinsLeaderBoardRequest) object;

        return entryCount == getWinsLeaderBoardRequest.entryCount &&
                Objects.equals(platform, getWinsLeaderBoardRequest.platform) &&
                Objects.equals(partyType, getWinsLeaderBoardRequest.partyType) &&
                Objects.equals(window, getWinsLeaderBoardRequest.window) &&
                Objects.equals(authHeaderValue, getWinsLeaderBoardRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platform, partyType, window, entryCount, authHeaderValue);
    }
}
