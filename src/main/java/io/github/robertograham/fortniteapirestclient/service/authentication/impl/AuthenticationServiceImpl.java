package io.github.robertograham.fortniteapirestclient.service.authentication.impl;

import io.github.robertograham.fortniteapirestclient.service.authentication.AuthenticationService;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetExchangeCodeRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.KillSessionRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthenticationServiceImpl implements AuthenticationService {

    private static Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final CloseableHttpClient httpClient;
    private final ResponseHandlerProvider responseHandlerProvider;

    public AuthenticationServiceImpl(CloseableHttpClient httpClient, ResponseHandlerProvider responseHandlerProvider) {
        this.httpClient = httpClient;
        this.responseHandlerProvider = responseHandlerProvider;
    }

    @Override
    public CompletableFuture<OAuthToken> getOAuthToken(GetOAuthTokenRequest getOAuthTokenRequest) {
        getOAuthTokenRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            OAuthToken oAuthToken;

            try {
                HttpPost httpPost = new HttpPost(Endpoint.OAUTH_TOKEN);
                httpPost.setEntity(new UrlEncodedFormEntity(Stream.concat(Stream.of(
                        new BasicNameValuePair("grant_type", getOAuthTokenRequest.getGrantType()),
                        new BasicNameValuePair("includePerms", "true")
                        ),
                        Arrays.stream(getOAuthTokenRequest.getAdditionalFormEntries())
                ).collect(Collectors.toList())));
                httpPost.setHeader(HttpHeaders.AUTHORIZATION, getOAuthTokenRequest.getAuthHeaderValue());

                oAuthToken = httpClient.execute(httpPost, responseHandlerProvider.handlerFor(OAuthToken.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return oAuthToken;
        }).handle((oAuthToken, throwable) -> {
            if (oAuthToken == null)
                LOG.error("Failed to fetch oauth token for request {}", getOAuthTokenRequest, throwable);

            return oAuthToken;
        });
    }

    @Override
    public CompletableFuture<ExchangeCode> getExchangeCode(GetExchangeCodeRequest getExchangeCodeRequest) {
        getExchangeCodeRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            ExchangeCode exchangeCode;

            HttpGet httpGet = new HttpGet(Endpoint.OAUTH_EXCHANGE);
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getExchangeCodeRequest.getAuthHeaderValue());

            try {
                exchangeCode = httpClient.execute(httpGet, responseHandlerProvider.handlerFor(ExchangeCode.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return exchangeCode;
        }).handle(((exchangeCode, throwable) -> {
            if (exchangeCode == null)
                LOG.error("Failed to fetch exchange code for request {}", getExchangeCodeRequest, throwable);

            return exchangeCode;
        }));
    }

    @Override
    public CompletableFuture<Boolean> killSession(KillSessionRequest killSessionRequest) {
        killSessionRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            HttpDelete httpDelete = new HttpDelete(Endpoint.killSession(killSessionRequest.getAccessToken()));
            httpDelete.addHeader(HttpHeaders.AUTHORIZATION, killSessionRequest.getAuthHeaderValue());

            try {
                httpClient.execute(httpDelete, responseHandlerProvider.stringHandler());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return "";
        }).handle((response, throwable) -> {
            if (response == null)
                LOG.error("Failed to kill session for request {}", killSessionRequest, throwable);

            return response != null;
        });
    }
}
