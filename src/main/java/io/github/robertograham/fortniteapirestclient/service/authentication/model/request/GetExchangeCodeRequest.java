package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetExchangeCodeRequest extends Request {

    private final String authorization;

    GetExchangeCodeRequest(String authorization) {
        this.authorization = authorization;
    }

    public static GetExchangeCodeRequestBuilder builder() {
        return new GetExchangeCodeRequestBuilder();
    }

    public String getAuthorization() {
        return authorization;
    }

    @Override
    public String toString() {
        return "GetExchangeCodeRequest{" +
                "authorization='" + authorization + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        GetExchangeCodeRequest that = (GetExchangeCodeRequest) object;

        return Objects.equals(authorization, that.authorization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorization);
    }
}
