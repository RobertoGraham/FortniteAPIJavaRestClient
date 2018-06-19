package io.github.robertograham.fortniteapirestclient.service.account.impl;

import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountsRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
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
    @DisplayName("getAccount returns account produced by response handler")
    void getAccount() throws IOException, ExecutionException, InterruptedException {
        String accountName = "accountName";
        String authHeaderValue = "authHeaderValue";

        Supplier<HttpGet> desiredHttpGetSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpGet>) () -> {
                    HttpGet httpGet = new HttpGet(Endpoint.lookup(accountName));
                    httpGet.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpGet;
                }).get().toString()));

        Account account = new Account();

        AccountResponseHandler handler = response -> account;

        GetAccountRequest getAccountRequest = mock(GetAccountRequest.class);

        when(getAccountRequest.getAccountName()).thenReturn(accountName);
        when(getAccountRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(responseHandlerProvider.handlerFor(Account.class)).thenReturn(handler);
        when(httpClient.execute(desiredHttpGetSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertEquals(accountService.getAccount(getAccountRequest).get(), account);

        verify(getAccountRequest).getAccountName();
        verify(getAccountRequest).getAuthHeaderValue();
        verify(responseHandlerProvider).handlerFor(Account.class);
        verify(httpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
    }

    @Test
    @DisplayName("getAccount returns null on IOException")
    void getAccountReturnsNull() throws IOException, ExecutionException, InterruptedException {
        String accountName = "accountName";
        String authHeaderValue = "authHeaderValue";

        Supplier<HttpGet> desiredHttpGetSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpGet>) () -> {
                    HttpGet httpGet = new HttpGet(Endpoint.lookup(accountName));
                    httpGet.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpGet;
                }).get().toString()));

        AccountResponseHandler handler = response -> null;

        GetAccountRequest getAccountRequest = mock(GetAccountRequest.class);

        when(getAccountRequest.getAccountName()).thenReturn(accountName);
        when(getAccountRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(responseHandlerProvider.handlerFor(Account.class)).thenReturn(handler);
        when(httpClient.execute(desiredHttpGetSupplier.get(), any(AccountResponseHandler.class))).thenThrow(IOException.class);

        assertNull(accountService.getAccount(getAccountRequest).get());

        verify(getAccountRequest, times(2)).getAccountName();
        verify(getAccountRequest).getAuthHeaderValue();
        verify(responseHandlerProvider).handlerFor(Account.class);
        verify(httpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
    }


    @Test
    @DisplayName("getAccounts returns list of correct size")
    void getAccounts() throws IOException, ExecutionException, InterruptedException {
        Set<String> accountIds = Stream.of("1", "2", "3").collect(Collectors.toSet());
        String authHeaderValue = "authHeaderValue";

        Supplier<HttpGet> desiredHttpGetSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpGet>) () -> {
                    HttpGet httpGet = new HttpGet(Endpoint.info(accountIds));
                    httpGet.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpGet;
                }).get().toString()));

        AccountArrayResponseHandler handler = response -> new Account[accountIds.size()];

        GetAccountsRequest getAccountsRequest = mock(GetAccountsRequest.class);

        when(getAccountsRequest.getAccountIds()).thenReturn(accountIds);
        when(getAccountsRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(responseHandlerProvider.handlerFor(Account[].class)).thenReturn(handler);
        when(httpClient.execute(desiredHttpGetSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertEquals(accountService.getAccounts(getAccountsRequest).get().size(), accountIds.size());

        verify(getAccountsRequest).getAccountIds();
        verify(getAccountsRequest).getAuthHeaderValue();
        verify(responseHandlerProvider).handlerFor(Account[].class);
        verify(httpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
    }

    @Test
    @DisplayName("getAccounts returns null on IOException")
    void getAccountsReturnsNull() throws IOException, ExecutionException, InterruptedException {
        Set<String> accountIds = Stream.of("1", "2", "3").collect(Collectors.toSet());
        String authHeaderValue = "authHeaderValue";

        Supplier<HttpGet> desiredHttpGetSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpGet>) () -> {
                    HttpGet httpGet = new HttpGet(Endpoint.info(accountIds));
                    httpGet.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpGet;
                }).get().toString()));

        AccountArrayResponseHandler handler = response -> null;

        GetAccountsRequest getAccountsRequest = mock(GetAccountsRequest.class);

        when(getAccountsRequest.getAccountIds()).thenReturn(accountIds);
        when(getAccountsRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(responseHandlerProvider.handlerFor(Account[].class)).thenReturn(handler);
        when(httpClient.execute(desiredHttpGetSupplier.get(), eq(handler))).thenThrow(IOException.class);

        assertNull(accountService.getAccounts(getAccountsRequest).get());

        verify(getAccountsRequest, Mockito.times(2)).getAccountIds();
        verify(getAccountsRequest).getAuthHeaderValue();
        verify(responseHandlerProvider).handlerFor(Account[].class);
        verify(httpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
    }

    private interface AccountResponseHandler extends ResponseHandler<Account> {
    }

    private interface AccountArrayResponseHandler extends ResponseHandler<Account[]> {
    }
}
