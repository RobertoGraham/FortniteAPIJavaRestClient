package io.github.robertograham.fortniteapirestclient.util;

import java.io.IOException;

public class FortniteApiErrorException extends IOException {

    public FortniteApiErrorException(FortniteApiError fortniteApiError, int statusCode) {
        super(statusCode + " - " + fortniteApiError.getErrorCode() + ": " + fortniteApiError.getErrorMessage());
    }
}
