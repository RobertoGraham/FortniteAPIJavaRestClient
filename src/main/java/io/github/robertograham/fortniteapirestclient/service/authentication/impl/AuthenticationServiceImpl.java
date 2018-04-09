package io.github.robertograham.fortniteapirestclient.service.authentication.impl;

import io.github.robertograham.fortniteapirestclient.service.authentication.AuthenticationService;
import io.github.robertograham.fortniteapirestclient.service.authentication.mapper.ExchangeCodeJsonExchangeCodeMapper;
import io.github.robertograham.fortniteapirestclient.service.authentication.mapper.OAuthTokenJsonOAuthTokenMapper;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetExchangeCodeRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuthenticationServiceImpl implements AuthenticationService {

    private ResponseHandler<String> responseHandler;
    private OAuthTokenJsonOAuthTokenMapper oAuthTokenJsonOAuthTokenMapper;
    private ExchangeCodeJsonExchangeCodeMapper exchangeCodeJsonExchangeCodeMapper;

    public AuthenticationServiceImpl() {
        responseHandler = new BasicResponseHandler();
        oAuthTokenJsonOAuthTokenMapper = new OAuthTokenJsonOAuthTokenMapper();
        exchangeCodeJsonExchangeCodeMapper = new ExchangeCodeJsonExchangeCodeMapper();
    }

    @Override
    public OAuthToken getOAuthToken(GetOAuthTokenRequest getOAuthTokenRequest) throws IOException {
        return oAuthTokenJsonOAuthTokenMapper.mapFrom(Request.Post(Endpoint.OAUTH_TOKEN)
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
                .handleResponse(responseHandler));
    }

    @Override
    public ExchangeCode getExchangeCode(GetExchangeCodeRequest getExchangeCodeRequest) throws IOException {
        return exchangeCodeJsonExchangeCodeMapper.mapFrom(Request.Get(Endpoint.OAUTH_EXCHANGE)
                .addHeader(HttpHeaders.AUTHORIZATION, getExchangeCodeRequest.getAuthHeaderValue())
                .execute()
                .handleResponse(responseHandler));
    }
}
