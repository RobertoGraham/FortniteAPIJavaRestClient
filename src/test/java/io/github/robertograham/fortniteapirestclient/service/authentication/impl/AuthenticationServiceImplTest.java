package io.github.robertograham.fortniteapirestclient.service.authentication.impl;

import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetExchangeCodeRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.KillSessionRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseRequestUtil;
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
    private CloseableHttpClient mockHttpClient;

    @Mock
    private ResponseRequestUtil mockResponseRequestUtil;

    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationServiceImpl(mockHttpClient, mockResponseRequestUtil);
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

        GetOAuthTokenRequest mockGetOAuthTokenRequest = mock(GetOAuthTokenRequest.class);

        when(mockGetOAuthTokenRequest.getGrantType()).thenReturn(grantType);
        when(mockGetOAuthTokenRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockGetOAuthTokenRequest.getAdditionalFormEntries()).thenReturn(additionalFormEntries);
        when(mockResponseRequestUtil.responseHandlerFor(OAuthToken.class)).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpPostSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertEquals(authenticationService.getOAuthToken(mockGetOAuthTokenRequest).get(), oAuthToken);

        verify(mockGetOAuthTokenRequest).getGrantType();
        verify(mockGetOAuthTokenRequest).getAuthHeaderValue();
        verify(mockGetOAuthTokenRequest).getAdditionalFormEntries();
        verify(mockResponseRequestUtil).responseHandlerFor(OAuthToken.class);
        verify(mockHttpClient).execute(desiredHttpPostSupplier.get(), eq(handler));
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

        GetOAuthTokenRequest mockGetOAuthTokenRequest = mock(GetOAuthTokenRequest.class);

        when(mockGetOAuthTokenRequest.getGrantType()).thenReturn(grantType);
        when(mockGetOAuthTokenRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockGetOAuthTokenRequest.getAdditionalFormEntries()).thenReturn(additionalFormEntries);
        when(mockResponseRequestUtil.responseHandlerFor(OAuthToken.class)).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpPostSupplier.get(), eq(handler))).thenThrow(IOException.class);

        assertNull(authenticationService.getOAuthToken(mockGetOAuthTokenRequest).get());

        verify(mockGetOAuthTokenRequest).getGrantType();
        verify(mockGetOAuthTokenRequest).getAuthHeaderValue();
        verify(mockGetOAuthTokenRequest).getAdditionalFormEntries();
        verify(mockResponseRequestUtil).responseHandlerFor(OAuthToken.class);
        verify(mockHttpClient).execute(desiredHttpPostSupplier.get(), eq(handler));
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

        GetExchangeCodeRequest mockGetExchangeCodeRequest = mock(GetExchangeCodeRequest.class);

        when(mockGetExchangeCodeRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockResponseRequestUtil.responseHandlerFor(ExchangeCode.class)).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpGetSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertEquals(authenticationService.getExchangeCode(mockGetExchangeCodeRequest).get(), exchangeCode);

        verify(mockGetExchangeCodeRequest).getAuthHeaderValue();
        verify(mockResponseRequestUtil).responseHandlerFor(ExchangeCode.class);
        verify(mockHttpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
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

        GetExchangeCodeRequest mockGetExchangeCodeRequest = mock(GetExchangeCodeRequest.class);

        when(mockGetExchangeCodeRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockResponseRequestUtil.responseHandlerFor(ExchangeCode.class)).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpGetSupplier.get(), eq(handler))).thenThrow(IOException.class);

        assertNull(authenticationService.getExchangeCode(mockGetExchangeCodeRequest).get());

        verify(mockGetExchangeCodeRequest).getAuthHeaderValue();
        verify(mockResponseRequestUtil).responseHandlerFor(ExchangeCode.class);
        verify(mockHttpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
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

        KillSessionRequest mockKillSessionRequest = mock(KillSessionRequest.class);

        when(mockKillSessionRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockKillSessionRequest.getAccessToken()).thenReturn(accessToken);
        when(mockResponseRequestUtil.stringResponseHandler()).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpDeleteSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertTrue(authenticationService.killSession(mockKillSessionRequest).get());

        verify(mockKillSessionRequest).getAuthHeaderValue();
        verify(mockKillSessionRequest).getAccessToken();
        verify(mockResponseRequestUtil).stringResponseHandler();
        verify(mockHttpClient).execute(desiredHttpDeleteSupplier.get(), eq(handler));
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

        KillSessionRequest mockKillSessionRequest = mock(KillSessionRequest.class);

        when(mockKillSessionRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockKillSessionRequest.getAccessToken()).thenReturn(accessToken);
        when(mockResponseRequestUtil.stringResponseHandler()).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpDeleteSupplier.get(), eq(handler))).thenThrow(IOException.class);

        assertFalse(authenticationService.killSession(mockKillSessionRequest).get());

        verify(mockKillSessionRequest).getAuthHeaderValue();
        verify(mockKillSessionRequest).getAccessToken();
        verify(mockResponseRequestUtil).stringResponseHandler();
        verify(mockHttpClient).execute(desiredHttpDeleteSupplier.get(), eq(handler));
    }

    private interface OAuthTokenResponeHandler extends ResponseHandler<OAuthToken> {
    }

    private interface ExchangeCodeResponeHandler extends ResponseHandler<ExchangeCode> {
    }

    private interface StringResponseHandler extends ResponseHandler<String> {
    }
}
