package io.github.robertograham.fortniteapirestclient;

import io.github.robertograham.fortniteapirestclient.domain.BattleRoyaleStats;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.domain.Player;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.StatsHelper;
import io.github.robertograham.fortniteapirestclient.util.mapper.impl.UserJsonPlayerMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FortniteApiRestClient {

    private static final Logger LOG = LoggerFactory.getLogger(FortniteApiRestClient.class);
    private final Credentials credentials;
    private final ScheduledExecutorService scheduler;
    private final UserJsonPlayerMapper userJsonPlayerMapper;
    private ScheduledFuture<?> checkTokenFuture;
    private LocalDateTime expiresAt;
    private String refreshToken;
    private String accessToken;
    private ResponseHandler<String> responseHandler;

    public FortniteApiRestClient(Credentials credentials) {
        this.credentials = credentials;

        responseHandler = new BasicResponseHandler();
        userJsonPlayerMapper = new UserJsonPlayerMapper();
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

                    String response = Request.Post(Endpoint.OAUTH_TOKEN)
                            .bodyForm(Form.form()
                                    .add("grant_type", "refresh_token")
                                    .add("refresh_token", refreshToken)
                                    .add("includePerms", "true")
                                    .build())
                            .addHeader(HttpHeaders.AUTHORIZATION, "basic " + credentials.getFortniteClientToken())
                            .addHeader(HttpHeaders.CONTENT_TYPE, URLEncodedUtils.CONTENT_TYPE)
                            .execute()
                            .handleResponse(responseHandler);

                    JSONObject jsonObject = new JSONObject(response);

                    expiresAt = LocalDateTime.ofInstant(Instant.parse(jsonObject.getString("expires_at")), ZoneOffset.UTC);
                    refreshToken = jsonObject.getString("refresh_token");
                    accessToken = jsonObject.getString("access_token");
                } catch (IOException e) {
                    LOG.error("IOException while refreshing expired tokens", e);
                }
        }, 1, 1, TimeUnit.SECONDS);
    }

    private String getUsernameAndPasswordDerivedAccessToken() throws IOException {
        String usernameAndPasswordDerivedTokenObjectJson = Request.Post(Endpoint.OAUTH_TOKEN)
                .bodyForm(Form.form()
                        .add("grant_type", "password")
                        .add("username", credentials.getEpicGamesEmailAddress())
                        .add("password", credentials.getEpicGamesPassword())
                        .add("includePerms", "true")
                        .build())
                .addHeader(HttpHeaders.AUTHORIZATION, "basic " + credentials.getEpicGamesLauncherToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, URLEncodedUtils.CONTENT_TYPE)
                .execute()
                .handleResponse(responseHandler);

        JSONObject usernameAndPasswordDerivedTokenObject = new JSONObject(usernameAndPasswordDerivedTokenObjectJson);

        return usernameAndPasswordDerivedTokenObject.getString("access_token");
    }

    private String getExchangeCode() throws IOException {
        String exchangeCodeObjectJson = Request.Get(Endpoint.OAUTH_EXCHANGE)
                .addHeader(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                .execute()
                .handleResponse(responseHandler);

        JSONObject exchangeCodeObject = new JSONObject(exchangeCodeObjectJson);

        return exchangeCodeObject.getString("code");
    }

    private String getFortniteApiTokenObjectJsonFromExchangeCode(String exchangeCode) throws IOException {
        return Request.Post(Endpoint.OAUTH_TOKEN)
                .bodyForm(Form.form()
                        .add("grant_type", "exchange_code")
                        .add("exchange_code", exchangeCode)
                        .add("token_type", "egl")
                        .add("includePerms", "true")
                        .build())
                .addHeader(HttpHeaders.AUTHORIZATION, "basic " + credentials.getFortniteClientToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, URLEncodedUtils.CONTENT_TYPE)
                .execute()
                .handleResponse(responseHandler);
    }

    private void login() throws IOException {
        accessToken = getUsernameAndPasswordDerivedAccessToken();

        String exchangeCode = getExchangeCode();

        String fortniteApiTokenObjectJson = getFortniteApiTokenObjectJsonFromExchangeCode(exchangeCode);

        JSONObject fortniteApiTokenObject = new JSONObject(fortniteApiTokenObjectJson);

        expiresAt = LocalDateTime.ofInstant(Instant.parse(fortniteApiTokenObject.getString("expires_at")), ZoneOffset.UTC);
        refreshToken = fortniteApiTokenObject.getString("refresh_token");
        accessToken = fortniteApiTokenObject.getString("access_token");
    }

    public Player getPlayer(String username) {
        String result = null;

        try {
            result = Request.Get(Endpoint.lookup(username))
                    .addHeader(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                    .execute()
                    .handleResponse(responseHandler);
        } catch (IOException e) {
            LOG.error("IOException while looking up user: {}", username, e);
        }

        return result == null ? null : userJsonPlayerMapper.map(result);
    }

    public BattleRoyaleStats getBattleRoyaleStats(String username, String platform) {
        BattleRoyaleStats[] result = new BattleRoyaleStats[1];

        Player player = getPlayer(username);

        if (player != null)
            try {
                String response = Request.Get(Endpoint.statsBattleRoyale(player.getId()))
                        .addHeader(HttpHeaders.AUTHORIZATION, "bearer " + accessToken)
                        .execute()
                        .handleResponse(responseHandler);

                if (StatsHelper.statsExistForPlatform(response, platform))
                    result[0] = StatsHelper.buildBattleRoyaleStats(response, player, platform);
            } catch (IOException e) {
                LOG.error("IOException while retrieving Battle Royale stats for user, {}, in platform: {}", username, platform, e);
            }

        return result[0];
    }

    public void close() {
        LOG.debug("Closing FortniteApiRestClient");

        checkTokenFuture.cancel(false);
        scheduler.shutdown();
    }
}
