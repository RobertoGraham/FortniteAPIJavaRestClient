package io.github.robertograham.fortniteapirestclient.service.account.impl;

import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class AccountServiceImplTest {

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private ResponseHandlerProvider responseHandlerProvider;

    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(httpClient, responseHandlerProvider);
    }

    @Test
    @DisplayName("Test getAccount")
    void getAccount() throws IOException {
        when(responseHandlerProvider.handlerFor(Account.class)).thenReturn(response -> null);

        GetAccountRequest getAccountRequest = mock(GetAccountRequest.class);

        when(getAccountRequest.getAccountName()).thenReturn("");
        when(getAccountRequest.getAuthHeaderValue()).thenReturn("");

        accountService.getAccount(getAccountRequest);

        verify(getAccountRequest).getAccountName();
        verify(getAccountRequest).getAuthHeaderValue();
        verify(responseHandlerProvider).handlerFor(Account.class);
        verify(httpClient).execute(any(HttpGet.class), any(ResponseHandler.class));
    }
}
