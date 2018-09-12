package io.github.robertograham.fortniteapirestclient;

import com.google.api.client.http.HttpRequestFactory;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoard;
import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatWindow;
import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.impl.AccountServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountsRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.AuthenticationService;
import io.github.robertograham.fortniteapirestclient.service.authentication.impl.AuthenticationServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetExchangeCodeRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.KillSessionRequest;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.LeaderBoardService;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.impl.LeaderBoardServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request.GetWinsLeaderBoardRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.StatisticsService;
import io.github.robertograham.fortniteapirestclient.service.statistics.impl.StatisticsServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FortniteApiRestClient implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(FortniteApiRestClient.class);
    private final Credentials credentials;
    private final AuthenticationService authenticationService;
    private final AccountService accountService;
    private final StatisticsService statisticsService;
    private final ScheduledExecutorService scheduledExecutorService;
    private final LeaderBoardService leaderBoardService;
    private final HttpRequestFactory httpRequestFactory;
    private OAuthToken sessionToken;

    FortniteApiRestClient(Credentials credentials, HttpRequestFactory httpRequestFactory, ScheduledExecutorService scheduledExecutorService, boolean autoLoginDisabled) {
        this.credentials = credentials;
        this.httpRequestFactory = httpRequestFactory;
        authenticationService = new AuthenticationServiceImpl(httpRequestFactory);
        accountService = new AccountServiceImpl(httpRequestFactory);
        statisticsService = new StatisticsServiceImpl(httpRequestFactory);
        leaderBoardService = new LeaderBoardServiceImpl(httpRequestFactory, accountService);
        this.scheduledExecutorService = scheduledExecutorService;

        scheduledExecutorService.scheduleWithFixedDelay(tokenRefreshRunnable(), 1, 1, TimeUnit.SECONDS);

        if (!autoLoginDisabled)
            try {
                login().get();
            } catch (InterruptedException | ExecutionException e) {
                LOG.error("Exception occurred during login process", e);
            }
    }

    public static FortniteApiRestClientBuilder builder(Credentials credentials) {
        return new FortniteApiRestClientBuilder(credentials);
    }

    private Runnable tokenRefreshRunnable() {
        return () -> {
            LocalDateTime now = LocalDateTime.now();
            LOG.debug("Validity of token that expires at {} being checked at {}", sessionToken != null ? sessionToken.getExpiresAt() : LocalDateTime.MIN, now);
            if (sessionToken != null && asLocalDateTime(sessionToken.getExpiresAt()).minusMinutes(15).isBefore(now)) {
                LOG.info("Token has expired - refreshing");

                try {
                    authenticationService.getOAuthToken(GetOAuthTokenRequest.builder()
                            .grantType("refresh_token")
                            .authorization("basic " + credentials.getFortniteClientToken())
                            .additionalFormEntries(Stream.of(
                                    new SimpleEntry<>("refresh_token", sessionToken.getRefreshToken())
                                    )
                                            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue))
                            )
                            .build())
                            .thenAcceptAsync(token -> Optional.ofNullable(token).ifPresent(this::setSessionToken))
                            .get();
                } catch (InterruptedException | ExecutionException e) {
                    LOG.error("Exception occurred during token refresh", e);
                }
            }
        };
    }

    private CompletableFuture<OAuthToken> getUsernameAndPasswordDerivedOAuthToken() {
        return authenticationService.getOAuthToken(GetOAuthTokenRequest.builder()
                .grantType("password")
                .authorization("basic " + credentials.getEpicGamesLauncherToken())
                .additionalFormEntries(Stream.of(
                        new SimpleEntry<>("username", credentials.getEpicGamesEmailAddress()),
                        new SimpleEntry<>("password", credentials.getEpicGamesPassword())
                        )
                                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue))
                )
                .build());
    }

    private CompletableFuture<ExchangeCode> getExchangeCode(OAuthToken usernameAndPasswordDerivedOAuthToken) {
        return authenticationService.getExchangeCode(GetExchangeCodeRequest.builder()
                .authorization("bearer " + usernameAndPasswordDerivedOAuthToken.getAccessToken())
                .build());
    }

    private CompletableFuture<OAuthToken> getFortniteApiOAuthTokenFromExchangeCode(ExchangeCode exchangeCode) {
        return authenticationService.getOAuthToken(GetOAuthTokenRequest.builder()
                .grantType("exchange_code")
                .authorization("basic " + credentials.getFortniteClientToken())
                .additionalFormEntries(
                        Stream.of(
                                new SimpleEntry<>("exchange_code", exchangeCode.getCode()),
                                new SimpleEntry<>("token_type", "eg1")
                        )
                                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue))
                )
                .build());
    }

    public CompletableFuture<Void> login() {
        return getUsernameAndPasswordDerivedOAuthToken()
                .thenComposeAsync(this::getExchangeCode)
                .thenComposeAsync(this::getFortniteApiOAuthTokenFromExchangeCode)
                .thenAcceptAsync(this::setSessionToken);
    }

    public CompletableFuture<Account> account(String accountName) {
        return accountService.getAccount(GetAccountRequest.builder()
                .accountName(accountName)
                .authorization("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    public CompletableFuture<List<Account>> accounts(String... accountIds) {
        return accountService.getAccounts(GetAccountsRequest.builder(new HashSet<>(Arrays.asList(accountIds)))
                .authorization("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    public CompletableFuture<StatsGroup> enhancedBattleRoyaleStatsByPlatform(String accountId, Platform platform, StatWindow window) {
        return statisticsService.getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.builder()
                .accountId(accountId)
                .window(window)
                .authorization("bearer " + nonNullableSessionToken().getAccessToken())
                .platform(platform)
                .build());
    }

    public CompletableFuture<Map<Platform, StatsGroup>> enhancedBattleRoyaleStats(String accountId, StatWindow window) {
        return statisticsService.getSoloDuoSquadBattleRoyaleStatistics(GetSoloDuoSquadBattleRoyaleStatisticsRequest.builder()
                .accountId(accountId)
                .window(window)
                .authorization("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    public CompletableFuture<List<Statistic>> battleRoyaleStats(String accountId, StatWindow window) {
        return statisticsService.getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.builder()
                .accountId(accountId)
                .window(window)
                .authorization("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    public CompletableFuture<LeaderBoard> winsLeaderBoard(Platform platform, PartyType partyType, StatWindow window, int entryCount) {
        return leaderBoardService.getWinsLeaderBoard(GetWinsLeaderBoardRequest.builder()
                .platform(platform)
                .partyType(partyType)
                .window(window)
                .authorization("bearer " + nonNullableSessionToken().getAccessToken())
                .entryCount(entryCount)
                .inAppId(nonNullableSessionToken().getInAppId())
                .build());
    }

    public CompletableFuture<EnhancedLeaderBoard> enhancedWinsLeaderBoard(Platform platform, PartyType partyType, StatWindow window, int entryCount) {
        return leaderBoardService.getEnhancedWinsLeaderBoard(GetWinsLeaderBoardRequest.builder()
                .platform(platform)
                .partyType(partyType)
                .window(window)
                .authorization("bearer " + nonNullableSessionToken().getAccessToken())
                .entryCount(entryCount)
                .inAppId(nonNullableSessionToken().getInAppId())
                .build());
    }

    private CompletableFuture<Boolean> killSession() {
        return authenticationService.killSession(KillSessionRequest.builder()
                .accessToken(sessionToken.getAccessToken())
                .authorization("bearer " + sessionToken.getAccessToken())
                .build());
    }

    private OAuthToken nonNullableSessionToken() {
        return Objects.requireNonNull(sessionToken, "Attempting to perform api operation while not logged in");
    }

    private void setSessionToken(OAuthToken oAuthToken) {
        sessionToken = oAuthToken;
    }

    public boolean isSessionValid() {
        return sessionToken != null && LocalDateTime.now().isBefore(asLocalDateTime(sessionToken.getExpiresAt()));
    }

    private LocalDateTime asLocalDateTime(String timestamp) {
        return LocalDateTime.ofInstant(Instant.parse(timestamp), ZoneOffset.UTC);
    }

    @Override
    public void close() {
        LOG.debug("Closing FortniteApiRestClient");

        scheduledExecutorService.shutdown();

        if (sessionToken != null)
            try {
                killSession().get();
            } catch (InterruptedException | ExecutionException e) {
                LOG.error("Exception occurred when killing session {}", sessionToken, e);
            }

        try {
            httpRequestFactory.getTransport().shutdown();
        } catch (IOException e) {
            LOG.error("IOException while closing HttpRequestFactory transport");
        }
    }
}
