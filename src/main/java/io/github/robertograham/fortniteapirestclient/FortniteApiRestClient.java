package io.github.robertograham.fortniteapirestclient;

import io.github.robertograham.fortniteapirestclient.domain.BattleRoyaleStats;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.domain.Player;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.StatsHelper;
import io.github.robertograham.fortniteapirestclient.util.StringResponseHandler;
import io.github.robertograham.fortniteapirestclient.util.mapper.impl.UserJsonPlayerMapper;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FortniteApiRestClient {

    private final Credentials credentials;
    private final ScheduledExecutorService scheduler;
    private final CloseableHttpClient httpClient;
    private static final Logger LOG = LoggerFactory.getLogger(FortniteApiRestClient.class);
    private final UserJsonPlayerMapper userJsonPlayerMapper;
    private ScheduledFuture<?> checkTokenFuture;
    private ResponseHandler<String> stringResponseHandler;
    private LocalDateTime expiresAt;
    private String refreshToken;
    private String accessToken;

    public FortniteApiRestClient(Credentials credentials) {
        this.credentials = credentials;

        userJsonPlayerMapper = new UserJsonPlayerMapper();
        stringResponseHandler = new StringResponseHandler();
        httpClient = HttpClients.createDefault();
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

                    List<NameValuePair> form = new ArrayList<>();
                    form.add(new BasicNameValuePair("grant_type", "refresh_token"));
                    form.add(new BasicNameValuePair("refresh_token", refreshToken));
                    form.add(new BasicNameValuePair("includePerms", "true"));

                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);

                    HttpPost httpPost = new HttpPost(Endpoint.OAUTH_TOKEN);
                    httpPost.setEntity(entity);
                    httpPost.setHeader("Authorization", "basic " + credentials.getFortniteClientToken());

                    String response = httpClient.execute(httpPost, stringResponseHandler);

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
        List<NameValuePair> form = new ArrayList<>();

        form.add(new BasicNameValuePair("grant_type", "password"));
        form.add(new BasicNameValuePair("username", credentials.getEpicGamesEmailAddress()));
        form.add(new BasicNameValuePair("password", credentials.getEpicGamesPassword()));
        form.add(new BasicNameValuePair("includePerms", "true"));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);

        HttpPost httpPost = new HttpPost(Endpoint.OAUTH_TOKEN);

        httpPost.setEntity(entity);
        httpPost.setHeader("Authorization", "basic " + credentials.getEpicGamesLauncherToken());

        JSONObject usernameAndPasswordDerivedTokenObject = new JSONObject(httpClient.execute(httpPost, stringResponseHandler));

        return usernameAndPasswordDerivedTokenObject.getString("access_token");
    }

    private String getExchangeCode() throws IOException {
        HttpGet httpGet = new HttpGet(Endpoint.OAUTH_EXCHANGE);

        httpGet.setHeader("Authorization", "bearer " + accessToken);

        JSONObject exchangeCodeObject = new JSONObject(httpClient.execute(httpGet, stringResponseHandler));

        return exchangeCodeObject.getString("code");
    }

    private String getFortniteApiTokenObjectJsonFromExchangeCode(String exchangeCode) throws IOException {
        List<NameValuePair> form = new ArrayList<>();

        form.add(new BasicNameValuePair("grant_type", "exchange_code"));
        form.add(new BasicNameValuePair("exchange_code", exchangeCode));
        form.add(new BasicNameValuePair("token_type", "egl"));
        form.add(new BasicNameValuePair("includePerms", "true"));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);

        HttpPost httpPost = new HttpPost(Endpoint.OAUTH_TOKEN);

        httpPost.setEntity(entity);
        httpPost.setHeader("Authorization", "basic " + credentials.getFortniteClientToken());

        return httpClient.execute(httpPost, stringResponseHandler);
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
            HttpGet httpGet = new HttpGet(Endpoint.lookup(username));
            httpGet.setHeader("Authorization", "bearer " + accessToken);

            result = httpClient.execute(httpGet, stringResponseHandler);
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
                HttpGet httpGet = new HttpGet(Endpoint.statsBattleRoyale(player.getId()));
                httpGet.setHeader("Authorization", "bearer " + accessToken);

                String response = httpClient.execute(httpGet, stringResponseHandler);

                if (StatsHelper.statsExistForPlatform(response, platform))
                    result[0] = StatsHelper.buildBattleRoyaleStats(response, player, platform);
            } catch (IOException e) {
                LOG.error("IOException while retrieving Battle Royale stats for user, {}, in platform: {}", username, platform, e);
            }

        return result[0];
    }

    public void close() {
        LOG.debug("Closing FortniteApiRestClient");

        try {
            httpClient.close();
        } catch (IOException e) {
            LOG.error("IOException while closing CloseableHttpClient", e);
        }

        checkTokenFuture.cancel(false);
        scheduler.shutdown();
    }
}
