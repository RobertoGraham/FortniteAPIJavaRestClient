package io.github.robertograham.fortniteapirestclient.service.leaderBoard.impl;

import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoard;
import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoardEntry;
import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.ExternalAuth;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountsRequest;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.LeaderBoardService;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoardEntry;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request.GetWinsLeaderBoardRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LeaderBoardServiceImpl implements LeaderBoardService {

    private static final Logger LOG = LoggerFactory.getLogger(LeaderBoardServiceImpl.class);
    private final CloseableHttpClient httpClient;
    private final ResponseHandlerProvider responseHandlerProvider;
    private final AccountService accountService;

    public LeaderBoardServiceImpl(CloseableHttpClient httpClient, ResponseHandlerProvider responseHandlerProvider, AccountService accountService) {
        this.httpClient = httpClient;
        this.responseHandlerProvider = responseHandlerProvider;
        this.accountService = accountService;
    }

    @Override
    public CompletableFuture<LeaderBoard> getWinsLeaderBoard(GetWinsLeaderBoardRequest getWinsLeaderBoardRequest) {
        return CompletableFuture.supplyAsync(() -> {
            LeaderBoard leaderBoard;

            HttpPost httpPost = new HttpPost(Endpoint.winsLeaderBoard(getWinsLeaderBoardRequest.getPlatform(), getWinsLeaderBoardRequest.getPartyType(), getWinsLeaderBoardRequest.getWindow(), getWinsLeaderBoardRequest.getEntryCount()));
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, getWinsLeaderBoardRequest.getAuthHeaderValue());

            try {
                leaderBoard = httpClient.execute(httpPost, responseHandlerProvider.handlerFor(LeaderBoard.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return leaderBoard;
        }).handle(((leaderBoard, throwable) -> {
            if (leaderBoard == null)
                LOG.error("Could not fetch leader board for platform {}, party type {}, window {}, and entry count {}", getWinsLeaderBoardRequest.getPlatform(), getWinsLeaderBoardRequest.getPartyType(), getWinsLeaderBoardRequest.getWindow(), getWinsLeaderBoardRequest.getEntryCount(), throwable);

            return leaderBoard;
        }));
    }

    @Override
    public CompletableFuture<EnhancedLeaderBoard> getEnhancedWinsLeaderBoard(GetWinsLeaderBoardRequest getWinsLeaderBoardRequest) {
        return getWinsLeaderBoard(getWinsLeaderBoardRequest).thenApply(leaderBoard -> {
            if (leaderBoard == null)
                return null;

            EnhancedLeaderBoard enhancedLeaderBoard = new EnhancedLeaderBoard();

            // final variable for lambda use
            List<Account> accounts = ((Supplier<List<Account>>) () -> {
                try {
                    return accountService.getAccounts(GetAccountsRequest.builder(leaderBoard.getEntries().stream()
                            .map(LeaderBoardEntry::getAccountId)
                            .map(id -> id.replace("-", ""))
                            .collect(Collectors.toSet()))
                            .authHeaderValue(getWinsLeaderBoardRequest.getAuthHeaderValue())
                            .build())
                            .get();
                } catch (InterruptedException | ExecutionException e) {
                    return new ArrayList<>();
                }
            }).get();

            enhancedLeaderBoard.setEntries(leaderBoard.getEntries().stream()
                    .map(entry -> {
                        EnhancedLeaderBoardEntry enhancedLeaderBoardEntry = new EnhancedLeaderBoardEntry();

                        Account matchingAccount = accounts.stream()
                                .filter(account -> account.getId().equals(entry.getAccountId().replace("-", "")))
                                .findFirst()
                                .orElse(null);

                        if (matchingAccount != null) {
                            String displayName = matchingAccount.getDisplayName();

                            if (displayName == null) {
                                List<ExternalAuth> externalAuths = new ArrayList<>(matchingAccount.getExternalAuths().values());

                                if (externalAuths.size() > 0)
                                    displayName = externalAuths.get(0).getExternalDisplayName();
                            }

                            enhancedLeaderBoardEntry.setDisplayName(displayName);
                        }

                        enhancedLeaderBoardEntry.setWins(entry.getValue());
                        enhancedLeaderBoardEntry.setRank(entry.getRank());
                        enhancedLeaderBoardEntry.setAccountId(entry.getAccountId());

                        return enhancedLeaderBoardEntry;
                    })
                    .collect(Collectors.toList()));

            return enhancedLeaderBoard;
        });
    }
}
