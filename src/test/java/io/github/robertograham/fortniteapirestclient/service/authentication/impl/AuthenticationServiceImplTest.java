package io.github.robertograham.fortniteapirestclient.service.authentication.impl;

import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("Test getOAuthToken returns token produced by response handler")
    void getAccount() throws IOException, ExecutionException, InterruptedException {
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

    private interface OAuthTokenResponeHandler extends ResponseHandler<OAuthToken> {
    }
}
