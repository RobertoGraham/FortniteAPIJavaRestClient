package io.github.robertograham.fortniteapirestclient.service.authentication.impl;

import io.github.robertograham.fortniteapirestclient.service.authentication.AuthenticationService;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetExchangeCodeRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.KillSessionRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ObjectMapperResponseHandler;
import org.apache.http.HttpHeaders;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public OAuthToken getOAuthToken(GetOAuthTokenRequest getOAuthTokenRequest) throws IOException {
        return Request.Post(Endpoint.OAUTH_TOKEN)
                .bodyForm(Stream.concat(Form.form()
                                .add("grant_type", getOAuthTokenRequest.getGrantType())
                                .add("includePerms", "true")
                                .build()
                                .stream(),
                        Arrays.stream(getOAuthTokenRequest.getAdditionalFormEntries()))
                        .collect(Collectors.toList()))
                .addHeader(HttpHeaders.AUTHORIZATION, getOAuthTokenRequest.getAuthHeaderValue())
                .addHeader(HttpHeaders.CONTENT_TYPE, URLEncodedUtils.CONTENT_TYPE)
                .execute()
                .handleResponse(ObjectMapperResponseHandler.thatProduces(OAuthToken.class));
    }

    @Override
    public ExchangeCode getExchangeCode(GetExchangeCodeRequest getExchangeCodeRequest) throws IOException {
        return Request.Get(Endpoint.OAUTH_EXCHANGE)
                .addHeader(HttpHeaders.AUTHORIZATION, getExchangeCodeRequest.getAuthHeaderValue())
                .execute()
                .handleResponse(ObjectMapperResponseHandler.thatProduces(ExchangeCode.class));
    }

    @Override
    public void killSession(KillSessionRequest killSessionRequest) throws IOException {
        Request.Delete(Endpoint.killSession(killSessionRequest.getAccessToken()))
                .addHeader(HttpHeaders.AUTHORIZATION, killSessionRequest.getAuthHeaderValue())
                .execute()
                .handleResponse(new BasicResponseHandler());
    }
}
