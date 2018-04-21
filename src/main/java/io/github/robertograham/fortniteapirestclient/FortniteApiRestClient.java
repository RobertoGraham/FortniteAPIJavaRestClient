package io.github.robertograham.fortniteapirestclient;

import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoard;
import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.impl.AccountServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
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
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    FortniteApiRestClient(Credentials credentials, CloseableHttpClient httpClient, ResponseHandlerProvider responseHandlerProvider, ScheduledExecutorService scheduledExecutorService, boolean autoLoginDisabled) {
        this.credentials = credentials;
        this.httpClient = httpClient;
        authenticationService = new AuthenticationServiceImpl(httpClient, responseHandlerProvider);
        accountService = new AccountServiceImpl(httpClient, responseHandlerProvider);
        statisticsService = new StatisticsServiceImpl(httpClient, responseHandlerProvider);
        leaderBoardService = new LeaderBoardServiceImpl(httpClient, responseHandlerProvider, accountService);
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
            LOG.debug("Checking if token is expired");

            if (sessionToken != null && sessionToken.getExpiresAt().minusMinutes(15).isBefore(LocalDateTime.now())) {
                LOG.info("Token has expired - refreshing");

                try {
                    authenticationService.getOAuthToken(GetOAuthTokenRequest.builder()
                            .grantType("refreshToken")
                            .authHeaderValue("basic " + credentials.getFortniteClientToken())
                            .additionalFormEntries(new NameValuePair[]{
                                    new BasicNameValuePair("refresh_token", sessionToken.getRefreshToken())
                            })
                            .build())
                            .thenAcceptAsync(this::setSessionToken)
                            .get();
                } catch (InterruptedException | ExecutionException e) {
                    LOG.error("Exception occured during token refresh", e);
                }
            }
        };
    }

    private CompletableFuture<OAuthToken> getUsernameAndPasswordDerivedOAuthToken() {
        return authenticationService.getOAuthToken(GetOAuthTokenRequest.builder()
                .grantType("password")
                .authHeaderValue("basic " + credentials.getEpicGamesLauncherToken())
                .additionalFormEntries(new NameValuePair[]{
                        new BasicNameValuePair("username", credentials.getEpicGamesEmailAddress()),
                        new BasicNameValuePair("password", credentials.getEpicGamesPassword())
                })
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
                .additionalFormEntries(new NameValuePair[]{
                        new BasicNameValuePair("exchange_code", exchangeCode.getCode()),
                        new BasicNameValuePair("token_type", "eg1")
                })
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
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());

    }

    public CompletableFuture<StatsGroup> enhancedBattleRoyaleStatsByPlatform(String accountId, String platform) {
        return statisticsService.getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.builder()
                .accountId(accountId)
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .platform(platform)
                .build());

    }

    public CompletableFuture<List<Statistic>> battleRoyaleStats(String accountId) {
        return statisticsService.getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.builder()
                .accountId(accountId)
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());

    }

    public CompletableFuture<LeaderBoard> winsLeaderBoard(String platform, String partyType) {
        return leaderBoardService.getWinsLeaderBoard(GetWinsLeaderBoardRequest.builder()
                .platform(platform)
                .partyType(partyType)
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());

    }

    public CompletableFuture<EnhancedLeaderBoard> enhancedWinsLeaderBoard(String platform, String partyType) {
        return leaderBoardService.getEnhancedWinsLeaderBoard(GetWinsLeaderBoardRequest.builder()
                .platform(platform)
                .partyType(partyType)
                .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                .build());

    }

    private CompletableFuture<Void> killSession() {
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

    public boolean isLoggedIn() {
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
