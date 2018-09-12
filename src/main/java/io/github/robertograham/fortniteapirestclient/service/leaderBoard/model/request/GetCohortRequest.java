package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetCohortRequest extends Request {

    private final String inAppId;
    private final Platform platform;
    private final PartyType partyType;
    private final String authorization;

    GetCohortRequest(String inAppId, Platform platform, PartyType partyType, String authorization) {
        this.inAppId = inAppId;
        this.platform = platform;
        this.partyType = partyType;
        this.authorization = authorization;
    }

    public static GetCohortRequestBuilder builder() {
        return new GetCohortRequestBuilder();
    }

    public String getInAppId() {
        return inAppId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public PartyType getPartyType() {
        return partyType;
    }

    public String getAuthorization() {
        return authorization;
    }

    @Override
    public String toString() {
        return "GetCohortRequest{" +
                "inAppId='" + inAppId + '\'' +
                ", platform='" + platform + '\'' +
                ", partyType='" + partyType + '\'' +
                ", authorization='" + authorization + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof GetCohortRequest))
            return false;

        GetCohortRequest getCohortRequest = (GetCohortRequest) object;

        return Objects.equals(inAppId, getCohortRequest.inAppId) &&
                Objects.equals(platform, getCohortRequest.platform) &&
                Objects.equals(partyType, getCohortRequest.partyType) &&
                Objects.equals(authorization, getCohortRequest.authorization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inAppId, platform, partyType, authorization);
    }
}
