package io.github.robertograham.fortniteapirestclient;

import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoard;
import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.impl.AccountServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.Eula;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.*;
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
import io.github.robertograham.fortniteapirestclient.util.ResponseRequestUtil;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FortniteApiRestClient implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(FortniteApiRestClient.class);
    private final Credentials credentials;
    private final AuthenticationService authenticationService;
    private final AccountService accountService;
    private final StatisticsService statisticsService;
    private final ScheduledExecutorService scheduledExecutorService;
    private final LeaderBoardService leaderBoardService;
    private final CloseableHttpClient httpClient;
    private OAuthToken sessionToken;
    private boolean acceptEulaOnLoginEnabled;

    FortniteApiRestClient(Credentials credentials, CloseableHttpClient httpClient, ResponseRequestUtil responseRequestUtil, ScheduledExecutorService scheduledExecutorService, boolean autoLoginDisabled, boolean acceptEulaOnLoginEnabled) {
        this.credentials = credentials;
        this.httpClient = httpClient;
        this.acceptEulaOnLoginEnabled = acceptEulaOnLoginEnabled;
        authenticationService = new AuthenticationServiceImpl(httpClient, responseRequestUtil);
        accountService = new AccountServiceImpl(httpClient, responseRequestUtil);
        statisticsService = new StatisticsServiceImpl(httpClient, responseRequestUtil);
        leaderBoardService = new LeaderBoardServiceImpl(httpClient, responseRequestUtil, accountService);
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
            if (sessionToken != null && sessionToken.getExpiresAt().minusMinutes(15).isBefore(now)) {
                LOG.info("Token has expired - refreshing");

                try {
                    authenticationService.getOAuthToken(GetOAuthTokenRequest.builder()
                            .grantType("refresh_token")
                            .authHeaderValue("basic " + credentials.getFortniteClientToken())
                            .additionalFormEntries(new NameValuePair[]{
                                    new BasicNameValuePair("refresh_token", sessionToken.getRefreshToken())
                            })
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
                .authHeaderValue("basic " + credentials.getEpicGamesLauncherToken())
                .additionalFormEntries(
                        new BasicNameValuePair("username", credentials.getEpicGamesEmailAddress()),
                        new BasicNameValuePair("password", credentials.getEpicGamesPassword())
                )
                .build());
    }

    private CompletableFuture<ExchangeCode> getExchangeCode(OAuthToken usernameAndPasswordDerivedOAuthToken) {
        return authenticationService.getExchangeCode(GetExchangeCodeRequest.builder()
                .authHeaderValue("bearer " + usernameAndPasswordDerivedOAuthToken.getAccessToken())
                .build());
    }

    private CompletableFuture<OAuthToken> getFortniteApiOAuthTokenFromExchangeCode(ExchangeCode exchangeCode) {
        return authenticationService.getOAuthToken(GetOAuthTokenRequest.builder()
                .grantType("exchange_code")
                .authHeaderValue("basic " + credentials.getFortniteClientToken())
                .additionalFormEntries(
                        new BasicNameValuePair("exchange_code", exchangeCode.getCode()),
                        new BasicNameValuePair("token_type", "eg1")
                )
                .build());
    }

    public CompletableFuture<Void> login() {
        return getUsernameAndPasswordDerivedOAuthToken()
                .thenComposeAsync(this::getExchangeCode)
                .thenComposeAsync(this::getFortniteApiOAuthTokenFromExchangeCode)
                .thenAcceptAsync(this::setSessionToken)
                .thenComposeAsync(voidInstance -> acceptEulaOnLoginEnabled ?
                        acceptEula()
                        : CompletableFuture.completedFuture(null));
    }

    private CompletableFuture<Eula> getEula() {
        return accountService.getEula(GetEulaRequest.builder()
                .accountId(nonNullableSessionToken().getAccountId())
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    private CompletableFuture<Boolean> acceptEula(Eula eula) {
        return eula == null ?
                CompletableFuture.completedFuture(false)
                : accountService.acceptEula(AcceptEulaRequest.builder()
                .version(eula.getVersion())
                .accountId(nonNullableSessionToken().getAccountId())
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    private CompletableFuture<Boolean> grantAccess() {
        return accountService.grantAccess(GrantAccessRequest.builder()
                .accountId(nonNullableSessionToken().getAccountId())
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    public CompletableFuture<Void> acceptEula() {
        return getEula()
                .thenComposeAsync(this::acceptEula)
                .thenAcceptAsync(isEulaAccepted -> grantAccess());
    }

    public CompletableFuture<Account> account(String accountName) {
        return accountService.getAccount(GetAccountRequest.builder()
                .accountName(accountName)
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    public CompletableFuture<List<Account>> accounts(String... accountIds) {
        return accountService.getAccounts(GetAccountsRequest.builder(new HashSet<>(Arrays.asList(accountIds)))
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    public CompletableFuture<StatsGroup> enhancedBattleRoyaleStatsByPlatform(String accountId, String platform, String window) {
        return statisticsService.getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.builder()
                .accountId(accountId)
                .window(window)
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .platform(platform)
                .build());
    }

    public CompletableFuture<Map<String, StatsGroup>> enhancedBattleRoyaleStats(String accountId, String window) {
        return statisticsService.getSoloDuoSquadBattleRoyaleStatistics(GetSoloDuoSquadBattleRoyaleStatisticsRequest.builder()
                .accountId(accountId)
                .window(window)
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    public CompletableFuture<List<Statistic>> battleRoyaleStats(String accountId, String window) {
        return statisticsService.getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.builder()
                .accountId(accountId)
                .window(window)
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());
    }

    public CompletableFuture<LeaderBoard> winsLeaderBoard(String platform, String partyType, String window, int entryCount) {
        return leaderBoardService.getWinsLeaderBoard(GetWinsLeaderBoardRequest.builder()
                .platform(platform)
                .partyType(partyType)
                .window(window)
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .entryCount(entryCount)
                .inAppId(nonNullableSessionToken().getInAppId())
                .build());
    }

    public CompletableFuture<EnhancedLeaderBoard> enhancedWinsLeaderBoard(String platform, String partyType, String window, int entryCount) {
        return leaderBoardService.getEnhancedWinsLeaderBoard(GetWinsLeaderBoardRequest.builder()
                .platform(platform)
                .partyType(partyType)
                .window(window)
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .entryCount(entryCount)
                .inAppId(nonNullableSessionToken().getInAppId())
                .build());
    }

    private CompletableFuture<Boolean> killSession() {
        return authenticationService.killSession(KillSessionRequest.builder()
                .accessToken(sessionToken.getAccessToken())
                .authHeaderValue("bearer " + sessionToken.getAccessToken())
                .build());
    }

    private OAuthToken nonNullableSessionToken() {
        return Objects.requireNonNull(sessionToken, "Attempting to perform api operation while not logged in");
    }

    private void setSessionToken(OAuthToken oAuthToken) {
        sessionToken = oAuthToken;
    }

    public boolean isSessionValid() {
        return sessionToken != null && LocalDateTime.now().isBefore(sessionToken.getExpiresAt());
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
            httpClient.close();
        } catch (IOException e) {
            LOG.error("IOException while closing CloseableHttpClient");
        }
    }
}
