package io.github.robertograham.fortniteapirestclient.service.authentication.impl;

import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetExchangeCodeRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.KillSessionRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class AuthenticationServiceImplTest {

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private ResponseHandlerProvider responseHandlerProvider;

    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationServiceImpl(httpClient, responseHandlerProvider);
    }

    @Test
    @DisplayName("getOAuthToken returns token produced by response handler")
    void getOAuthToken() throws IOException, ExecutionException, InterruptedException {
        String grantType = "grantType";
        String authHeaderValue = "authHeaderValue";
        NameValuePair[] additionalFormEntries = new NameValuePair[]{new BasicNameValuePair("name", "value")};

        Supplier<HttpPost> desiredHttpPostSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpPost>) () -> {
                    HttpPost httpPost = new HttpPost(Endpoint.OAUTH_TOKEN);

                    try {
                        httpPost.setEntity(new UrlEncodedFormEntity(Stream.concat(Stream.of(
                                new BasicNameValuePair("grant_type", grantType),
                                new BasicNameValuePair("includePerms", "true")
                                ),
                                Arrays.stream(additionalFormEntries)
                        ).collect(Collectors.toList())));
                    } catch (UnsupportedEncodingException ignored) {
                    }

                    httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpPost;
                }).get().toString()));

        OAuthToken oAuthToken = new OAuthToken();

        OAuthTokenResponeHandler handler = response -> oAuthToken;

        GetOAuthTokenRequest getOAuthTokenRequest = mock(GetOAuthTokenRequest.class);

        when(getOAuthTokenRequest.getGrantType()).thenReturn(grantType);
        when(getOAuthTokenRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(getOAuthTokenRequest.getAdditionalFormEntries()).thenReturn(additionalFormEntries);
        when(responseHandlerProvider.handlerFor(OAuthToken.class)).thenReturn(handler);
        when(httpClient.execute(desiredHttpPostSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertEquals(authenticationService.getOAuthToken(getOAuthTokenRequest).get(), oAuthToken);

        verify(getOAuthTokenRequest).getGrantType();
        verify(getOAuthTokenRequest).getAuthHeaderValue();
        verify(getOAuthTokenRequest).getAdditionalFormEntries();
        verify(responseHandlerProvider).handlerFor(OAuthToken.class);
        verify(httpClient).execute(desiredHttpPostSupplier.get(), eq(handler));
    }

    @Test
    @DisplayName("getOAuthToken returns null on IOException")
    void getOAuthTokenReturnsNull() throws IOException, ExecutionException, InterruptedException {
        String grantType = "grantType";
        String authHeaderValue = "authHeaderValue";
        NameValuePair[] additionalFormEntries = new NameValuePair[]{new BasicNameValuePair("name", "value")};

        Supplier<HttpPost> desiredHttpPostSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpPost>) () -> {
                    HttpPost httpPost = new HttpPost(Endpoint.OAUTH_TOKEN);

                    try {
                        httpPost.setEntity(new UrlEncodedFormEntity(Stream.concat(Stream.of(
                                new BasicNameValuePair("grant_type", grantType),
                                new BasicNameValuePair("includePerms", "true")
                                ),
                                Arrays.stream(additionalFormEntries)
                        ).collect(Collectors.toList())));
                    } catch (UnsupportedEncodingException ignored) {
                    }

                    httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpPost;
                }).get().toString()));

        OAuthTokenResponeHandler handler = response -> null;

        GetOAuthTokenRequest getOAuthTokenRequest = mock(GetOAuthTokenRequest.class);

        when(getOAuthTokenRequest.getGrantType()).thenReturn(grantType);
        when(getOAuthTokenRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(getOAuthTokenRequest.getAdditionalFormEntries()).thenReturn(additionalFormEntries);
        when(responseHandlerProvider.handlerFor(OAuthToken.class)).thenReturn(handler);
        when(httpClient.execute(desiredHttpPostSupplier.get(), eq(handler))).thenThrow(IOException.class);

        assertNull(authenticationService.getOAuthToken(getOAuthTokenRequest).get());

        verify(getOAuthTokenRequest).getGrantType();
        verify(getOAuthTokenRequest).getAuthHeaderValue();
        verify(getOAuthTokenRequest).getAdditionalFormEntries();
        verify(responseHandlerProvider).handlerFor(OAuthToken.class);
        verify(httpClient).execute(desiredHttpPostSupplier.get(), eq(handler));
    }

    @Test
    @DisplayName("getExchangeCode returns exchange code produced by response handler")
    void getExchangeCode() throws IOException, ExecutionException, InterruptedException {
        String authHeaderValue = "authHeaderValue";

        Supplier<HttpGet> desiredHttpGetSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpGet>) () -> {
                    HttpGet httpGet = new HttpGet(Endpoint.OAUTH_EXCHANGE);
                    httpGet.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpGet;
                }).get().toString()));

        ExchangeCode exchangeCode = new ExchangeCode();

        ExchangeCodeResponeHandler handler = response -> exchangeCode;

        GetExchangeCodeRequest getExchangeCodeRequest = mock(GetExchangeCodeRequest.class);

        when(getExchangeCodeRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(responseHandlerProvider.handlerFor(ExchangeCode.class)).thenReturn(handler);
        when(httpClient.execute(desiredHttpGetSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertEquals(authenticationService.getExchangeCode(getExchangeCodeRequest).get(), exchangeCode);

        verify(getExchangeCodeRequest).getAuthHeaderValue();
        verify(responseHandlerProvider).handlerFor(ExchangeCode.class);
        verify(httpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
    }

    @Test
    @DisplayName("getExchangeCode returns null on IOException")
    void getExchangeCodeReturnsNull() throws IOException, ExecutionException, InterruptedException {
        String authHeaderValue = "authHeaderValue";

        Supplier<HttpGet> desiredHttpGetSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpGet>) () -> {
                    HttpGet httpGet = new HttpGet(Endpoint.OAUTH_EXCHANGE);
                    httpGet.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpGet;
                }).get().toString()));

        ExchangeCodeResponeHandler handler = response -> null;

        GetExchangeCodeRequest getExchangeCodeRequest = mock(GetExchangeCodeRequest.class);

        when(getExchangeCodeRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(responseHandlerProvider.handlerFor(ExchangeCode.class)).thenReturn(handler);
        when(httpClient.execute(desiredHttpGetSupplier.get(), eq(handler))).thenThrow(IOException.class);

        assertNull(authenticationService.getExchangeCode(getExchangeCodeRequest).get());

        verify(getExchangeCodeRequest).getAuthHeaderValue();
        verify(responseHandlerProvider).handlerFor(ExchangeCode.class);
        verify(httpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
    }

    @Test
    @DisplayName("killSession returns true")
    void killSession() throws IOException, ExecutionException, InterruptedException {
        String authHeaderValue = "authHeaderValue";
        String accessToken = "accessToken";

        Supplier<HttpDelete> desiredHttpDeleteSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpDelete>) () -> {
                    HttpDelete httpDelete = new HttpDelete(Endpoint.killSession(accessToken));
                    httpDelete.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpDelete;
                }).get().toString()));

        StringResponseHandler handler = response -> "";

        KillSessionRequest killSessionRequest = mock(KillSessionRequest.class);

        when(killSessionRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(killSessionRequest.getAccessToken()).thenReturn(accessToken);
        when(responseHandlerProvider.stringHandler()).thenReturn(handler);
        when(httpClient.execute(desiredHttpDeleteSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertTrue(authenticationService.killSession(killSessionRequest).get());

        verify(killSessionRequest).getAuthHeaderValue();
        verify(killSessionRequest).getAccessToken();
        verify(responseHandlerProvider).stringHandler();
        verify(httpClient).execute(desiredHttpDeleteSupplier.get(), eq(handler));
    }

    @Test
    @DisplayName("killSession returns false on IOException")
    void killSessionReturnsFalse() throws IOException, ExecutionException, InterruptedException {
        String authHeaderValue = "authHeaderValue";
        String accessToken = "accessToken";

        Supplier<HttpDelete> desiredHttpDeleteSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpDelete>) () -> {
                    HttpDelete httpDelete = new HttpDelete(Endpoint.killSession(accessToken));
                    httpDelete.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpDelete;
                }).get().toString()));

        StringResponseHandler handler = response -> null;

        KillSessionRequest killSessionRequest = mock(KillSessionRequest.class);

        when(killSessionRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(killSessionRequest.getAccessToken()).thenReturn(accessToken);
        when(responseHandlerProvider.stringHandler()).thenReturn(handler);
        when(httpClient.execute(desiredHttpDeleteSupplier.get(), eq(handler))).thenThrow(IOException.class);

        assertFalse(authenticationService.killSession(killSessionRequest).get());

        verify(killSessionRequest).getAuthHeaderValue();
        verify(killSessionRequest).getAccessToken();
        verify(responseHandlerProvider).stringHandler();
        verify(httpClient).execute(desiredHttpDeleteSupplier.get(), eq(handler));
    }

    private interface OAuthTokenResponeHandler extends ResponseHandler<OAuthToken> {
    }

    private interface ExchangeCodeResponeHandler extends ResponseHandler<ExchangeCode> {
    }

    private interface StringResponseHandler extends ResponseHandler<String> {
    }
}
