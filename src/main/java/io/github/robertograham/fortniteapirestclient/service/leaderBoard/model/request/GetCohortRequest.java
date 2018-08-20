package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetCohortRequest extends Request {

    private final String inAppId;
    private final String platform;
    private final String partyType;
    private final String authHeaderValue;

    GetCohortRequest(String inAppId, String platform, String partyType, String authHeaderValue) {
        this.inAppId = inAppId;
        this.platform = platform;
        this.partyType = partyType;
        this.authHeaderValue = authHeaderValue;
    }

    public static GetCohortRequestBuilder builder() {
        return new GetCohortRequestBuilder();
    }

    public String getInAppId() {
        return inAppId;
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

    @Override
    public String toString() {
        return "GetCohortRequest{" +
                "inAppId='" + inAppId + '\'' +
                ", platform='" + platform + '\'' +
                ", partyType='" + partyType + '\'' +
                ", authHeaderValue='" + authHeaderValue + '\'' +
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
                Objects.equals(authHeaderValue, getCohortRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inAppId, platform, partyType, authHeaderValue);
    }
}
