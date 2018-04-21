package io.github.robertograham.fortniteapirestclient.service.authentication;

import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetExchangeCodeRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.KillSessionRequest;

import java.util.concurrent.CompletableFuture;

public interface AuthenticationService {

    CompletableFuture<OAuthToken> getOAuthToken(GetOAuthTokenRequest getOAuthTokenRequest);

    CompletableFuture<ExchangeCode> getExchangeCode(GetExchangeCodeRequest getExchangeCodeRequest);

    CompletableFuture<Void> killSession(KillSessionRequest killSessionRequest);
}
