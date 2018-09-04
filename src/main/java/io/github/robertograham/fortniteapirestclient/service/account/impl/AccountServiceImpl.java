package io.github.robertograham.fortniteapirestclient.service.account.impl;

import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.Eula;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.*;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseRequestUtil;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
    private static final int MAX_ID_COUNT = 100;
    private final CloseableHttpClient httpClient;
    private final ResponseRequestUtil responseRequestUtil;

    public AccountServiceImpl(CloseableHttpClient httpClient, ResponseRequestUtil responseRequestUtil) {
        this.httpClient = httpClient;
        this.responseRequestUtil = responseRequestUtil;
    }

    @Override
    public CompletableFuture<Account> getAccount(GetAccountRequest getAccountRequest) {
        getAccountRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            Account account;

            HttpGet httpGet = new HttpGet(Endpoint.lookup(getAccountRequest.getAccountName()));
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getAccountRequest.getAuthHeaderValue());

            try {
                account = httpClient.execute(httpGet, responseRequestUtil.responseHandlerFor(Account.class));
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
        getAccountsRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            Set<Set<String>> accountIdsPartitioned = getAccountIdsPartitioned(getAccountsRequest.getAccountIds());

            List<CompletableFuture<Account[]>> futures = accountIdsPartitioned.stream()
                    .map(accountIdPartition -> CompletableFuture.supplyAsync(() -> {
                        Account[] accounts;

                        HttpGet httpGet = new HttpGet(Endpoint.info(accountIdPartition));
                        httpGet.addHeader(HttpHeaders.AUTHORIZATION, getAccountsRequest.getAuthHeaderValue());

                        try {
                            accounts = httpClient.execute(httpGet, responseRequestUtil.responseHandlerFor(Account[].class));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        return accounts;
                    }).handleAsync((accounts, throwable) -> {
                        if (accounts == null) {
                            LOG.error("Failed to fetch accounts for account ids {}", accountIdPartition, throwable);

                            return null;
                        }

                        return accounts;
                    }))
                    .collect(Collectors.toList());
            try {
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            return futures.stream()
                    .map(CompletableFuture::join)
                    .filter(Objects::nonNull)
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());
        }).handle((accounts, throwable) -> {
            if (accounts == null) {
                LOG.error("Failed to fetch all accounts for account ids {}", getAccountsRequest.getAccountIds(), throwable);

                return null;
            } else if (accounts.size() != getAccountsRequest.getAccountIds().size())
                LOG.warn("Failed to fetch some accounts for account ids {}", getAccountsRequest.getAccountIds());

            return accounts.size() > 0 ? accounts : null;
        });
    }

    @Override
    public CompletableFuture<Eula> getEula(GetEulaRequest getEulaRequest) {
        getEulaRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            Eula eula;

            HttpGet httpGet = new HttpGet(Endpoint.getEula(getEulaRequest.getAccountId()));
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getEulaRequest.getAuthHeaderValue());

            try {
                eula = httpClient.execute(httpGet, responseRequestUtil.responseHandlerFor(Eula.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return eula;
        }).handle((eula, throwable) -> {
            if (eula == null)
                LOG.error("EULA for account id {} not found", getEulaRequest.getAccountId(), throwable);

            return eula;
        });
    }

    @Override
    public CompletableFuture<Boolean> acceptEula(AcceptEulaRequest acceptEulaRequest) {
        acceptEulaRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            HttpPost httpPost = new HttpPost(Endpoint.acceptEula(acceptEulaRequest.getVersion(), acceptEulaRequest.getAccountId()));
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, acceptEulaRequest.getAuthHeaderValue());

            try {
                httpClient.execute(httpPost, responseRequestUtil.stringResponseHandler());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return "";
        }).handle((response, throwable) -> {
            if (response == null)
                LOG.error("Failed to accept EULA for account id {}, and version {}", acceptEulaRequest.getAccountId(), acceptEulaRequest.getVersion(), throwable);

            return response != null;
        });
    }

    @Override
    public CompletableFuture<Boolean> grantAccess(GrantAccessRequest grantAccessRequest) {
        grantAccessRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            HttpPost httpPost = new HttpPost(Endpoint.grantAccess(grantAccessRequest.getAccountId()));
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, grantAccessRequest.getAuthHeaderValue());

            try {
                httpClient.execute(httpPost, responseRequestUtil.stringResponseHandler());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return "";
        }).handle((response, throwable) -> {
            if (response == null)
                LOG.error("Failed to grant access for account id {}", grantAccessRequest.getAccountId(), throwable);

            return response != null;
        });
    }

    private Set<Set<String>> getAccountIdsPartitioned(Collection<String> accountIds) {
        List<String> accountIdList = new ArrayList<>(accountIds);

        return IntStream.range(0, accountIdList.size())
                .boxed()
                .collect(Collectors.groupingBy(index -> index / MAX_ID_COUNT))
                .values()
                .stream()
                .map(indices -> indices.stream()
                        .map(accountIdList::get)
                        .collect(Collectors.toSet()))
                .collect(Collectors.toSet());
    }
}
