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
    private CloseableHttpClient mockHttpClient;

    @Mock
    private ResponseHandlerProvider mockResponseHandlerProvider;

    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(mockHttpClient, mockResponseHandlerProvider);
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

        GetAccountRequest mockGetAccountRequest = mock(GetAccountRequest.class);

        when(mockGetAccountRequest.getAccountName()).thenReturn(accountName);
        when(mockGetAccountRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockResponseHandlerProvider.handlerFor(Account.class)).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpGetSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertEquals(accountService.getAccount(mockGetAccountRequest).get(), account);

        verify(mockGetAccountRequest).getAccountName();
        verify(mockGetAccountRequest).getAuthHeaderValue();
        verify(mockResponseHandlerProvider).handlerFor(Account.class);
        verify(mockHttpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
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

        GetAccountRequest mockGetAccountRequest = mock(GetAccountRequest.class);

        when(mockGetAccountRequest.getAccountName()).thenReturn(accountName);
        when(mockGetAccountRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockResponseHandlerProvider.handlerFor(Account.class)).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpGetSupplier.get(), any(AccountResponseHandler.class))).thenThrow(IOException.class);

        assertNull(accountService.getAccount(mockGetAccountRequest).get());

        verify(mockGetAccountRequest, times(2)).getAccountName();
        verify(mockGetAccountRequest).getAuthHeaderValue();
        verify(mockResponseHandlerProvider).handlerFor(Account.class);
        verify(mockHttpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
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

        GetAccountsRequest mockGetAccountsRequest = mock(GetAccountsRequest.class);

        when(mockGetAccountsRequest.getAccountIds()).thenReturn(accountIds);
        when(mockGetAccountsRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockResponseHandlerProvider.handlerFor(Account[].class)).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpGetSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertEquals(accountService.getAccounts(mockGetAccountsRequest).get().size(), accountIds.size());

        verify(mockGetAccountsRequest).getAccountIds();
        verify(mockGetAccountsRequest).getAuthHeaderValue();
        verify(mockResponseHandlerProvider).handlerFor(Account[].class);
        verify(mockHttpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
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

        GetAccountsRequest mockGetAccountsRequest = mock(GetAccountsRequest.class);

        when(mockGetAccountsRequest.getAccountIds()).thenReturn(accountIds);
        when(mockGetAccountsRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockResponseHandlerProvider.handlerFor(Account[].class)).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpGetSupplier.get(), eq(handler))).thenThrow(IOException.class);

        assertNull(accountService.getAccounts(mockGetAccountsRequest).get());

        verify(mockGetAccountsRequest, Mockito.times(2)).getAccountIds();
        verify(mockGetAccountsRequest).getAuthHeaderValue();
        verify(mockResponseHandlerProvider).handlerFor(Account[].class);
        verify(mockHttpClient).execute(desiredHttpGetSupplier.get(), eq(handler));
    }

    private interface AccountResponseHandler extends ResponseHandler<Account> {
    }

    private interface AccountArrayResponseHandler extends ResponseHandler<Account[]> {
    }
}
