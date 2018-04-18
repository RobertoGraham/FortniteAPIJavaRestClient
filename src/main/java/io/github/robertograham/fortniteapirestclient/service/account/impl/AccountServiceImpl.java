package io.github.robertograham.fortniteapirestclient.service.account.impl;

import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountsRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final CloseableHttpClient httpClient;
    private final ResponseHandlerProvider responseHandlerProvider;

    public AccountServiceImpl(CloseableHttpClient httpClient, ResponseHandlerProvider responseHandlerProvider) {
        this.httpClient = httpClient;
        this.responseHandlerProvider = responseHandlerProvider;
    }

    @Override
    public Account getAccount(GetAccountRequest getAccountRequest) throws IOException {
        HttpGet httpGet = new HttpGet(Endpoint.lookup(getAccountRequest.getAccountName()));
        httpGet.addHeader(HttpHeaders.AUTHORIZATION, getAccountRequest.getAuthHeaderValue());

        return httpClient.execute(httpGet, responseHandlerProvider.handlerFor(Account.class));
    }

    @Override
    public List<Account> getAccounts(GetAccountsRequest getAccountsRequest) throws IOException {
        HttpGet httpGet = new HttpGet(Endpoint.info(getAccountsRequest.getAccountIds()));
        httpGet.addHeader(HttpHeaders.AUTHORIZATION, getAccountsRequest.getAuthHeaderValue());

        Account[] accounts = httpClient.execute(httpGet, responseHandlerProvider.handlerFor(Account[].class));

        return accounts == null ? null : Arrays.asList(accounts);
    }
}
