package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class KillSessionRequest extends Request {

    private final String accessToken;
    private final String authorization;

    KillSessionRequest(String accessToken, String authorization) {
        this.accessToken = accessToken;
        this.authorization = authorization;
    }

    public static KillSessionRequestBuilder builder() {
        return new KillSessionRequestBuilder();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAuthorization() {
        return authorization;
    }

    @Override
    public String toString() {
        return "KillSessionRequest{" +
                "accessToken='" + accessToken + '\'' +
                ", authorization='" + authorization + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        KillSessionRequest killSessionRequest = (KillSessionRequest) object;

        return Objects.equals(accessToken, killSessionRequest.accessToken) &&
                Objects.equals(authorization, killSessionRequest.authorization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, authorization);
    }
}
