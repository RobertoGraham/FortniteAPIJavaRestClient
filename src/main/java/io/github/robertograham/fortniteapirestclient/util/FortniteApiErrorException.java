package io.github.robertograham.fortniteapirestclient.util;

import org.apache.http.client.ClientProtocolException;

public class FortniteApiErrorException extends ClientProtocolException {

    public FortniteApiErrorException(FortniteApiError fortniteApiError, int statusCode) {
        super(String.format("%s - %s: %s", statusCode, fortniteApiError.getErrorCode(), fortniteApiError.getErrorMessage()));
    }
}
