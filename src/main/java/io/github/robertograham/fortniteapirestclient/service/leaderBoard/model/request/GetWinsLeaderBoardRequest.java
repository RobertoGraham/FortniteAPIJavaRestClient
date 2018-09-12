package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatWindow;
import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetWinsLeaderBoardRequest extends Request {
    private final Platform platform;
    private final PartyType partyType;
    private final StatWindow window;
    private final String authorization;
    private final int entryCount;
    private final String inAppId;

    GetWinsLeaderBoardRequest(Platform platform, PartyType partyType, StatWindow window, String authorization, int entryCount, String inAppId) {
        this.platform = platform;
        this.partyType = partyType;
        this.window = window;
        this.authorization = authorization;
        this.entryCount = entryCount;
        this.inAppId = inAppId;
    }

    public static GetWinsLeaderBoardRequestBuilder builder() {
        return new GetWinsLeaderBoardRequestBuilder();
    }

    public Platform getPlatform() {
        return platform;
    }

    public PartyType getPartyType() {
        return partyType;
    }

    public StatWindow getWindow() {
        return window;
    }

    public String getAuthorization() {
        return authorization;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public String getInAppId() {
        return inAppId;
    }

    @Override
    public String toString() {
        return "GetWinsLeaderBoardRequest{" +
                "platform='" + platform + '\'' +
                ", partyType='" + partyType + '\'' +
                ", window='" + window + '\'' +
                ", authorization='" + authorization + '\'' +
                ", entryCount=" + entryCount +
                ", inAppId='" + inAppId + '\'' +
                '}';
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
                Objects.equals(authorization, getWinsLeaderBoardRequest.authorization) &&
                Objects.equals(inAppId, getWinsLeaderBoardRequest.inAppId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platform, partyType, window, authorization, entryCount, inAppId);
    }
}
