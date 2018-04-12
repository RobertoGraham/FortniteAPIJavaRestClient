package io.github.robertograham.fortniteapirestclient;

import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.AuthenticationService;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetExchangeCodeRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.GetOAuthTokenRequest;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.request.KillSessionRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.StatisticsService;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FortniteApiRestClient implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(FortniteApiRestClient.class);
    private final Credentials credentials;
    private final AuthenticationService authenticationService;
    private final AccountService accountService;
    private final StatisticsService statisticsService;
    private final ScheduledExecutorService scheduledExecutorService;
    private OAuthToken sessionToken;

    FortniteApiRestClient(Credentials credentials, AuthenticationService authenticationService, AccountService accountService, StatisticsService statisticsService, ScheduledExecutorService scheduledExecutorService, boolean autoLoginDisabled) {
        this.credentials = credentials;
        this.authenticationService = authenticationService;
        this.accountService = accountService;
        this.statisticsService = statisticsService;
        this.scheduledExecutorService = scheduledExecutorService;

        scheduledExecutorService.scheduleWithFixedDelay(tokenRefreshRunnable(), 1, 1, TimeUnit.SECONDS);

        if (!autoLoginDisabled)
            try {
                login();
            } catch (IOException e) {
                LOG.error("IOException during login process", e);
            }
    }

    public static FortniteApiRestClientBuilder builder(Credentials credentials) {
        return new FortniteApiRestClientBuilder(credentials);
    }

    private Runnable tokenRefreshRunnable() {
        return () -> {
            LOG.debug("Checking if token is expired");

            if (sessionToken != null && sessionToken.getExpiresAt().minusMinutes(15).isBefore(LocalDateTime.now()))
                try {
                    LOG.info("Token has expired - refreshing");

                    sessionToken = authenticationService.getOAuthToken(GetOAuthTokenRequest.builder()
                            .grantType("refreshToken")
                            .authHeaderValue("basic " + credentials.getFortniteClientToken())
                            .additionalFormEntries(new NameValuePair[]{
                                    new BasicNameValuePair("refresh_token", sessionToken.getRefreshToken())
                            })
                            .build());
                } catch (IOException e) {
                    LOG.error("IOException while refreshing expired tokens", e);
                }
        };
    }

    private OAuthToken getUsernameAndPasswordDerivedOAuthToken() throws IOException {
        return authenticationService.getOAuthToken(GetOAuthTokenRequest.builder()
                .grantType("password")
                .authHeaderValue("basic " + credentials.getEpicGamesLauncherToken())
                .additionalFormEntries(new NameValuePair[]{
                        new BasicNameValuePair("username", credentials.getEpicGamesEmailAddress()),
                        new BasicNameValuePair("password", credentials.getEpicGamesPassword())
                })
                .build());
    }

    private ExchangeCode getExchangeCode(OAuthToken usernameAndPasswordDerivedOAuthToken) throws IOException {
        return authenticationService.getExchangeCode(GetExchangeCodeRequest.builder()
                .authHeaderValue("bearer " + usernameAndPasswordDerivedOAuthToken.getAccessToken())
                .build());
    }

    private OAuthToken getFortniteApiOAuthTokenFromExchangeCode(ExchangeCode exchangeCode) throws IOException {
        return authenticationService.getOAuthToken(GetOAuthTokenRequest.builder()
                .grantType("exchange_code")
                .authHeaderValue("basic " + credentials.getFortniteClientToken())
                .additionalFormEntries(new NameValuePair[]{
                        new BasicNameValuePair("exchange_code", exchangeCode.getCode()),
                        new BasicNameValuePair("token_type", "egl")
                })
                .build());
    }

    public void login() throws IOException {
        OAuthToken usernameAndPasswordDerivedOAuthToken = getUsernameAndPasswordDerivedOAuthToken();

        ExchangeCode exchangeCode = getExchangeCode(usernameAndPasswordDerivedOAuthToken);

        sessionToken = getFortniteApiOAuthTokenFromExchangeCode(exchangeCode);
    }

    public Account getAccount(String accountName) {
        try {
            return accountService.getAccount(GetAccountRequest.builder()
                    .accountName(accountName)
                    .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                    .build());
        } catch (IOException e) {
            LOG.error("IOException while looking up account: {}", accountName, e);
        }

        return null;
    }

    public StatsGroup getEnhancedBattleRoyaleStatsByPlatform(String accountId, String platform) {
        try {
            return statisticsService.getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.builder()
                    .accountId(accountId)
                    .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                    .platform(platform)
                    .build());
        } catch (IOException e) {
            LOG.error("IOException while looking up stats on platform: {}, for accountId: {}", platform, accountId, e);
        }

        return null;
    }

    public List<Statistic> getBattleRoyaleStats(String accountId) {
        try {
            return statisticsService.getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.builder()
                    .accountId(accountId)
                    .authHeaderValue("bearer " + nonNullableSessionToken().getAccessToken())
                    .build());
        } catch (IOException e) {
            LOG.error("IOException while looking up stats for accountId: {}", accountId, e);
        }

        return null;
    }

    private void killSession() {
        try {
            authenticationService.killSession(KillSessionRequest.builder()
                    .accessToken(sessionToken.getAccessToken())
                    .authHeaderValue("bearer " + sessionToken.getAccessToken())
                    .build());
        } catch (IOException e) {
            LOG.error("IOException while killing session for accessToken: {}", sessionToken.getAccessToken(), e);
        }
    }

    private OAuthToken nonNullableSessionToken() {
        return Objects.requireNonNull(sessionToken, "Attempting to perform api operation while not logged in");
    }

    @Override
    public void close() {
        LOG.debug("Closing FortniteApiRestClient");

        scheduledExecutorService.shutdown();

        if (sessionToken != null)
            killSession();
    }
}
