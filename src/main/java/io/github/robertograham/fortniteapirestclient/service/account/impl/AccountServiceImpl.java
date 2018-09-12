package io.github.robertograham.fortniteapirestclient.service.account.impl;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import io.github.robertograham.fortniteapirestclient.EpicGamesUrl;
import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountsRequest;
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
    private final HttpRequestFactory httpRequestFactory;

    public AccountServiceImpl(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }

    @Override
    public CompletableFuture<Account> getAccount(GetAccountRequest getAccountRequest) {
        getAccountRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            Account account;

            try {
                account = httpRequestFactory.buildGetRequest(EpicGamesUrl.accountByUsername(getAccountRequest.getAccountName()))
                        .setHeaders(new HttpHeaders().setAuthorization(getAccountRequest.getAuthorization()))
                        .execute()
                        .parseAs(Account.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return account;
        }).handleAsync((account, throwable) -> {
            if (throwable != null)
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

                        try {
                            accounts = httpRequestFactory.buildGetRequest(EpicGamesUrl.accountById(accountIdPartition))
                                    .setHeaders(new HttpHeaders().setAuthorization(getAccountsRequest.getAuthorization()))
                                    .execute()
                                    .parseAs(Account[].class);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        return accounts;
                    }).handleAsync((accounts, throwable) -> {
                        if (throwable != null) {
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
        }).handleAsync((accounts, throwable) -> {
            if (accounts == null) {
                LOG.error("Failed to fetch all accounts for account ids {}", getAccountsRequest.getAccountIds(), throwable);

                return null;
            } else if (accounts.size() != getAccountsRequest.getAccountIds().size())
                LOG.warn("Failed to fetch some accounts for account ids {}", getAccountsRequest.getAccountIds());

            return accounts.size() > 0 ? accounts : null;
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
