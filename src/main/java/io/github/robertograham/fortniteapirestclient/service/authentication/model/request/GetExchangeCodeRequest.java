package io.github.robertograham.fortniteapirestclient.service.authentication.model.request;

import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetExchangeCodeRequest extends Request {

    private final String authHeaderValue;

    GetExchangeCodeRequest(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;
    }

    public static GetExchangeCodeRequestBuilder builder() {
        return new GetExchangeCodeRequestBuilder();
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    @Override
    public String toString() {
        return "GetExchangeCodeRequest{" +
                "authHeaderValue='" + authHeaderValue + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        GetExchangeCodeRequest that = (GetExchangeCodeRequest) object;

        return Objects.equals(authHeaderValue, that.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authHeaderValue);
    }
}
