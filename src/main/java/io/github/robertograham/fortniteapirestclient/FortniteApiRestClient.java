package io.github.robertograham.fortniteapirestclient;

import io.github.robertograham.fortniteapirestclient.domain.Credentials;
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
import io.github.robertograham.fortniteapirestclient.service.statistics.StatisticsService;
import io.github.robertograham.fortniteapirestclient.service.statistics.impl.StatisticsServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FortniteApiRestClient {

    private static final Logger LOG = LoggerFactory.getLogger(FortniteApiRestClient.class);
    private final Credentials credentials;
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> checkTokenFuture;
    private LocalDateTime expiresAt;
    private String refreshToken;
    private String accessToken;
    private AuthenticationService authenticationService;
    private AccountService accountService;
    private StatisticsService statisticsService;

    public FortniteApiRestClient(Credentials credentials) {
        this.credentials = credentials;

        authenticationService = new AuthenticationServiceImpl();
        accountService = new AccountServiceImpl();
        statisticsService = new StatisticsServiceImpl();
        scheduler = Executors.newScheduledThreadPool(1);
        checkTokenFuture = checkToken();

        try {
            login();
        } catch (IOException e) {
            LOG.error("IOException during login process", e);
            throw new IllegalStateException("FortniteApiRestClient could not complete login process");
        }
    }

    private ScheduledFuture<?> checkToken() {
        return scheduler.scheduleWithFixedDelay(() -> {
            LOG.debug("Checking if token is expired");

            if (expiresAt != null && expiresAt.minusMinutes(15).isBefore(LocalDateTime.now()))
                try {
                    LOG.info("Token is expired");

                    expiresAt = null;

                    OAuthToken oAuthToken = authenticationService.getOAuthToken(GetOAuthTokenRequest.Builder.newInstance()
                            .grantType("refreshToken")
                            .authHeaderValue("basic " + credentials.getFortniteClientToken())
                            .additionalFormEntries(new NameValuePair[]{
                                    new BasicNameValuePair("refresh_token", refreshToken)
                            })
                            .build());

                    expiresAt = oAuthToken.getExpiresAt();
                    refreshToken = oAuthToken.getRefreshToken();
                    accessToken = oAuthToken.getAccessToken();
                } catch (IOException e) {
                    LOG.error("IOException while refreshing expired tokens", e);
                }
        }, 1, 1, TimeUnit.SECONDS);
    }

    private OAuthToken getUsernameAndPasswordDerivedOAuthToken() throws IOException {
        return authenticationService.getOAuthToken(GetOAuthTokenRequest.Builder.newInstance()
                .grantType("password")
                .authHeaderValue("basic " + credentials.getEpicGamesLauncherToken())
                .additionalFormEntries(new NameValuePair[]{
                        new BasicNameValuePair("username", credentials.getEpicGamesEmailAddress()),
                        new BasicNameValuePair("password", credentials.getEpicGamesPassword())
                })
                .build());
    }

    private ExchangeCode getExchangeCode(OAuthToken usernameAndPasswordDerivedOAuthToken) throws IOException {
        return authenticationService.getExchangeCode(GetExchangeCodeRequest.Builder.newInstance()
                .authHeaderValue("bearer " + usernameAndPasswordDerivedOAuthToken.getAccessToken())
                .build());
    }

    private OAuthToken getFortniteApiOAuthTokenFromExchangeCode(ExchangeCode exchangeCode) throws IOException {
        return authenticationService.getOAuthToken(GetOAuthTokenRequest.Builder.newInstance()
                .grantType("exchange_code")
                .authHeaderValue("basic " + credentials.getFortniteClientToken())
                .additionalFormEntries(new NameValuePair[]{
                        new BasicNameValuePair("exchange_code", exchangeCode.getCode()),
                        new BasicNameValuePair("token_type", "egl")
                })
                .build());
    }

    private void login() throws IOException {
        OAuthToken usernameAndPasswordDerivedOAuthToken = getUsernameAndPasswordDerivedOAuthToken();

        ExchangeCode exchangeCode = getExchangeCode(usernameAndPasswordDerivedOAuthToken);

        OAuthToken fortniteApiTokenObjectJson = getFortniteApiOAuthTokenFromExchangeCode(exchangeCode);

        expiresAt = fortniteApiTokenObjectJson.getExpiresAt();
        refreshToken = fortniteApiTokenObjectJson.getRefreshToken();
        accessToken = fortniteApiTokenObjectJson.getAccessToken();
    }

    public Account getAccount(String accountName) {
        try {
            return accountService.getAccount(GetAccountRequest.Builder.newInstance()
                    .accountName(accountName)
                    .authHeaderValue("bearer " + accessToken)
                    .build());
        } catch (IOException e) {
            LOG.error("IOException while looking up account: {}", accountName, e);
        }

        return null;
    }

    public StatsGroup getEnhancedBattleRoyaleStatsByPlatform(String accountId, String platform) {
        try {
            return statisticsService.getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.Builder.newInstance()
                    .accountId(accountId)
                    .authHeaderValue("bearer " + accessToken)
                    .platform(platform)
                    .build());
        } catch (IOException e) {
            LOG.error("IOException while looking up stats on platform: {}, for accountId: {}", platform, accountId, e);
        }

        return null;
    }

    public List<Statistic> getBattleRoyaleStats(String accountId) {
        try {
            return statisticsService.getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.Builder.newInstance()
                    .accountId(accountId)
                    .authHeaderValue("bearer " + accessToken)
                    .build());
        } catch (IOException e) {
            LOG.error("IOException while looking up stats for accountId: {}", accountId, e);
        }

        return null;
    }

    private void killSession() {
        try {
            authenticationService.killSession(KillSessionRequest.Builder.newInstance()
                    .accessToken(accessToken)
                    .authHeaderValue("bearer " + accessToken)
                    .build());
        } catch (IOException e) {
            LOG.error("IOException while killing session for accessToken: {}", accessToken, e);
        }
    }

    public void close() {
        LOG.debug("Closing FortniteApiRestClient");

        killSession();
        checkTokenFuture.cancel(false);
        scheduler.shutdown();
    }
}
