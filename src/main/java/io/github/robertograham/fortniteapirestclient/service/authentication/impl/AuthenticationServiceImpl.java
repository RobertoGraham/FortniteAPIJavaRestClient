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

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final CloseableHttpClient httpClient;
    private final ResponseHandlerProvider responseHandlerProvider;

    public AuthenticationServiceImpl(CloseableHttpClient httpClient, ResponseHandlerProvider responseHandlerProvider) {
        this.httpClient = httpClient;
        this.responseHandlerProvider = responseHandlerProvider;
    }

    @Override
    public OAuthToken getOAuthToken(GetOAuthTokenRequest getOAuthTokenRequest) throws IOException {
        HttpPost httpPost = new HttpPost(Endpoint.OAUTH_TOKEN);
        httpPost.setEntity(new UrlEncodedFormEntity(Stream.concat(Stream.of(
                new BasicNameValuePair("grant_type", getOAuthTokenRequest.getGrantType()),
                new BasicNameValuePair("includePerms", "true")
                ),
                Arrays.stream(getOAuthTokenRequest.getAdditionalFormEntries())
        ).collect(Collectors.toList())));
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, getOAuthTokenRequest.getAuthHeaderValue());

        return httpClient.execute(httpPost, responseHandlerProvider.handlerFor(OAuthToken.class));
    }

    @Override
    public ExchangeCode getExchangeCode(GetExchangeCodeRequest getExchangeCodeRequest) throws IOException {
        HttpGet httpGet = new HttpGet(Endpoint.OAUTH_EXCHANGE);
        httpGet.addHeader(HttpHeaders.AUTHORIZATION, getExchangeCodeRequest.getAuthHeaderValue());

        return httpClient.execute(httpGet, responseHandlerProvider.handlerFor(ExchangeCode.class));
    }

    @Override
    public void killSession(KillSessionRequest killSessionRequest) throws IOException {
        HttpDelete httpDelete = new HttpDelete(Endpoint.killSession(killSessionRequest.getAccessToken()));
        httpDelete.addHeader(HttpHeaders.AUTHORIZATION, killSessionRequest.getAuthHeaderValue());

        httpClient.execute(httpDelete, responseHandlerProvider.stringHandler());
    }
}
