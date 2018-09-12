package io.github.robertograham.fortniteapirestclient.service.leaderBoard.impl;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.json.JsonHttpContent;
import io.github.robertograham.fortniteapirestclient.EpicGamesRequestInitializer;
import io.github.robertograham.fortniteapirestclient.EpicGamesUrl;
import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoard;
import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoardEntry;
import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountsRequest;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.LeaderBoardService;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.Cohort;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoardEntry;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request.GetCohortRequest;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request.GetWinsLeaderBoardRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LeaderBoardServiceImpl implements LeaderBoardService {

    private static final Logger LOG = LoggerFactory.getLogger(LeaderBoardServiceImpl.class);
    private final HttpRequestFactory httpRequestFactory;
    private final AccountService accountService;

    public LeaderBoardServiceImpl(HttpRequestFactory httpRequestFactory, AccountService accountService) {
        this.httpRequestFactory = httpRequestFactory;
        this.accountService = accountService;
    }

    @Override
    public CompletableFuture<LeaderBoard> getWinsLeaderBoard(GetWinsLeaderBoardRequest getWinsLeaderBoardRequest) {
        getWinsLeaderBoardRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            LeaderBoard leaderBoard;
            Cohort cohort = null;

            try {
                cohort = getCohort(
                        GetCohortRequest.builder()
                                .inAppId(getWinsLeaderBoardRequest.getInAppId())
                                .authorization(getWinsLeaderBoardRequest.getAuthorization())
                                .partyType(getWinsLeaderBoardRequest.getPartyType())
                                .platform(getWinsLeaderBoardRequest.getPlatform())
                                .build()
                )
                        .get();
            } catch (InterruptedException | ExecutionException ignored) {
                LOG.error("Could not fetch cohort for inAppId: {}, platform: {}, and partyType: {}", getWinsLeaderBoardRequest.getInAppId(), getWinsLeaderBoardRequest.getPlatform(), getWinsLeaderBoardRequest.getPartyType());
            }

            List<String> cohortAccounts = Optional.ofNullable(cohort)
                    .map(Cohort::getCohortAccounts)
                    .orElse(new ArrayList<>());

            try {
                leaderBoard = httpRequestFactory.buildPostRequest(
                        EpicGamesUrl.winsLeaderBoard(getWinsLeaderBoardRequest.getPlatform(), getWinsLeaderBoardRequest.getPartyType(), getWinsLeaderBoardRequest.getWindow(), getWinsLeaderBoardRequest.getEntryCount()),
                        new JsonHttpContent(EpicGamesRequestInitializer.JSON_FACTORY, cohortAccounts)
                )
                        .setHeaders(new HttpHeaders().setAuthorization(getWinsLeaderBoardRequest.getAuthorization()))
                        .execute()
                        .parseAs(LeaderBoard.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return leaderBoard;
        }).handleAsync(((leaderBoard, throwable) -> {
            if (throwable != null)
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
                                            .authorization(getWinsLeaderBoardRequest.getAuthorization())
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

    @Override
    public CompletableFuture<Cohort> getCohort(GetCohortRequest getCohortRequest) {
        getCohortRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            Cohort cohort;

            try {
                cohort = httpRequestFactory.buildGetRequest(EpicGamesUrl.cohort(getCohortRequest.getInAppId(), getCohortRequest.getPlatform(), getCohortRequest.getPartyType()))
                        .setHeaders(new HttpHeaders().setAuthorization(getCohortRequest.getAuthorization()))
                        .execute()
                        .parseAs(Cohort.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return cohort;
        }).handleAsync(((cohort, throwable) -> {
            if (throwable != null)
                LOG.error("Could not fetch cohort for {}", getCohortRequest, throwable);

            return cohort;
        }));
    }
}
