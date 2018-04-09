package io.github.robertograham.fortniteapirestclient.service.authentication;

import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetExchangeCodeRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;

import java.io.IOException;

public interface AuthenticationService {

    OAuthToken getOAuthToken(GetOAuthTokenRequest getOAuthTokenRequest) throws IOException;

    ExchangeCode getExchangeCode(GetExchangeCodeRequest getExchangeCodeRequest) throws IOException;
}
