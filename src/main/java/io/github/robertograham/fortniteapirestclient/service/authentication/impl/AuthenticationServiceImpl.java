package io.github.robertograham.fortniteapirestclient.service.authentication.impl;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.UrlEncodedContent;
import io.github.robertograham.fortniteapirestclient.EpicGamesUrl;
import io.github.robertograham.fortniteapirestclient.service.authentication.AuthenticationService;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetExchangeCodeRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.KillSessionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthenticationServiceImpl implements AuthenticationService {

    private static Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final HttpRequestFactory httpRequestFactory;

    public AuthenticationServiceImpl(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }

    @Override
    public CompletableFuture<OAuthToken> getOAuthToken(GetOAuthTokenRequest getOAuthTokenRequest) {
        getOAuthTokenRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            OAuthToken oAuthToken;

            try {
                Map<String, String> data = Stream.concat(
                        getOAuthTokenRequest.getAdditionalFormEntries().entrySet().stream(),
                        Stream.of(
                                new SimpleEntry<>("grant_type", getOAuthTokenRequest.getGrantType()),
                                new SimpleEntry<>("includePerms", "true")
                        )
                )
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                oAuthToken = httpRequestFactory.buildPostRequest(EpicGamesUrl.oAuthToken(), new UrlEncodedContent(data))
                        .setHeaders(new HttpHeaders().setAuthorization(getOAuthTokenRequest.getAuthorization()))
                        .execute()
                        .parseAs(OAuthToken.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return oAuthToken;
        }).handleAsync((oAuthToken, throwable) -> {
            if (throwable != null)
                LOG.error("Failed to fetch oauth token for request {}", getOAuthTokenRequest, throwable);

            return oAuthToken;
        });
    }

    @Override
    public CompletableFuture<ExchangeCode> getExchangeCode(GetExchangeCodeRequest getExchangeCodeRequest) {
        getExchangeCodeRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            ExchangeCode exchangeCode;

            try {
                exchangeCode = httpRequestFactory.buildGetRequest(EpicGamesUrl.oAuthExchange())
                        .setHeaders(new HttpHeaders().setAuthorization(getExchangeCodeRequest.getAuthorization()))
                        .execute()
                        .parseAs(ExchangeCode.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return exchangeCode;
        }).handleAsync(((exchangeCode, throwable) -> {
            if (throwable != null)
                LOG.error("Failed to fetch exchange code for request {}", getExchangeCodeRequest, throwable);

            return exchangeCode;
        }));
    }

    @Override
    public CompletableFuture<Boolean> killSession(KillSessionRequest request) {
        request.log();

        return CompletableFuture.supplyAsync(() -> {
            try {
                httpRequestFactory.buildDeleteRequest(EpicGamesUrl.killSession(request.getAccessToken()))
                        .setHeaders(new HttpHeaders().setAuthorization(request.getAuthorization()))
                        .execute()
                        .parseAsString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return "";
        }).handleAsync((response, throwable) -> {
            if (throwable != null)
                LOG.error("Failed to kill session for request {}", request, throwable);

            return response != null;
        });
    }
}
