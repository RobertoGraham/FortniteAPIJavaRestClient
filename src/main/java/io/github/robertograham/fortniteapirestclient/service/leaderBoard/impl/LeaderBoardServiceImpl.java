package io.github.robertograham.fortniteapirestclient.service.leaderBoard.impl;

import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoard;
import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoardEntry;
import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountsRequest;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.LeaderBoardService;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoardEntry;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request.GetWinsLeaderBoardRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
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
        getWinsLeaderBoardRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            LeaderBoard leaderBoard;

            HttpPost httpPost = new HttpPost(Endpoint.winsLeaderBoard(getWinsLeaderBoardRequest.getPlatform(), getWinsLeaderBoardRequest.getPartyType(), getWinsLeaderBoardRequest.getWindow(), getWinsLeaderBoardRequest.getEntryCount()));
            httpPost.setEntity(new StringEntity("[]", StandardCharsets.UTF_8));
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, getWinsLeaderBoardRequest.getAuthHeaderValue());
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            try {
                leaderBoard = httpClient.execute(httpPost, responseHandlerProvider.handlerFor(LeaderBoard.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return leaderBoard;
        }).handle(((leaderBoard, throwable) -> {
            if (leaderBoard == null)
                LOG.error("Could not fetch leader board for {}", getWinsLeaderBoardRequest, throwable);

            return leaderBoard;
        }));
    }

    @Override
    public CompletableFuture<EnhancedLeaderBoard> getEnhancedWinsLeaderBoard(GetWinsLeaderBoardRequest getWinsLeaderBoardRequest) {
        getWinsLeaderBoardRequest.log();

        return getWinsLeaderBoard(getWinsLeaderBoardRequest).thenApply(leaderBoard -> {
            if (leaderBoard == null)
                return null;

            EnhancedLeaderBoard enhancedLeaderBoard = new EnhancedLeaderBoard();

            // final variable for lambda use
            Map<String, Account> accountIdToAccountMap = ((Supplier<Map<String, Account>>) () -> {
                try {
                    return Optional.ofNullable(
                            accountService.getAccounts(
                                    GetAccountsRequest.builder(
                                            leaderBoard.getEntries().stream()
                                                    .map(LeaderBoardEntry::getAccountId)
                                                    .map(id -> id.replace("-", ""))
                                                    .collect(Collectors.toSet())
                                    )
                                            .authHeaderValue(getWinsLeaderBoardRequest.getAuthHeaderValue())
                                            .build()
                            )
                                    .get()
                    )
                            .orElse(new ArrayList<>())
                            .stream()
                            .peek(account -> account.setId(account.getId().replace("-", "")))
                            .collect(Collectors.toMap(Account::getId, Function.identity()));
                } catch (InterruptedException | ExecutionException e) {
                    return new HashMap<>();
                }
            }).get();

            enhancedLeaderBoard.setEntries(leaderBoard.getEntries().stream()
                    .map(entry -> {
                        EnhancedLeaderBoardEntry enhancedLeaderBoardEntry = new EnhancedLeaderBoardEntry();
                        enhancedLeaderBoardEntry.setAccount(accountIdToAccountMap.get(entry.getAccountId().replace("-", "")));
                        enhancedLeaderBoardEntry.setWins(entry.getValue());
                        enhancedLeaderBoardEntry.setRank(entry.getRank());
                        return enhancedLeaderBoardEntry;
                    })
                    .collect(Collectors.toList()));

            return enhancedLeaderBoard;
        });
    }
}
