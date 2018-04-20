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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final CloseableHttpClient httpClient;
    private final ResponseHandlerProvider responseHandlerProvider;

    public AccountServiceImpl(CloseableHttpClient httpClient, ResponseHandlerProvider responseHandlerProvider) {
        this.httpClient = httpClient;
        this.responseHandlerProvider = responseHandlerProvider;
    }

    @Override
    public CompletableFuture<Account> getAccount(GetAccountRequest getAccountRequest) {
        return CompletableFuture.supplyAsync(() -> {
            Account account;

            HttpGet httpGet = new HttpGet(Endpoint.lookup(getAccountRequest.getAccountName()));
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getAccountRequest.getAuthHeaderValue());

            try {
                account = httpClient.execute(httpGet, responseHandlerProvider.handlerFor(Account.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return account;
        }).handle((account, throwable) -> {
            if (account == null)
                LOG.error("Account for name {} not found", getAccountRequest.getAccountName(), throwable);

            return account;
        });
    }

    @Override
    public CompletableFuture<List<Account>> getAccounts(GetAccountsRequest getAccountsRequest) {
        return CompletableFuture.supplyAsync(() -> {
            Account[] accounts;

            HttpGet httpGet = new HttpGet(Endpoint.info(getAccountsRequest.getAccountIds()));
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getAccountsRequest.getAuthHeaderValue());

            try {
                accounts = httpClient.execute(httpGet, responseHandlerProvider.handlerFor(Account[].class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return accounts;
        }).handle(((accounts, throwable) -> {
            if (accounts == null) {
                LOG.error("Failed to fetch accounts for account ids {}", getAccountsRequest.getAccountIds(), throwable);

                return null;
            }

            return Arrays.asList(accounts);
        }));
    }
}
