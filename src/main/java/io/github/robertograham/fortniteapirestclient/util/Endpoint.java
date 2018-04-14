package io.github.robertograham.fortniteapirestclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class Endpoint {

    public static final String OAUTH_TOKEN = "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/token";
    public static final String OAUTH_EXCHANGE = "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/exchange";

    private static final Logger LOG = LoggerFactory.getLogger(Endpoint.class);

    public static String lookup(String username) {
        try {
            return "https://persona-public-service-prod06.ol.epicgames.com/persona/api/public/account/lookup?q=" + new URI(null, null, username, null).getRawPath();
        } catch (URISyntaxException e) {
            LOG.error("URISyntaxException while encoding username: {}", username, e);
            return "https://persona-public-service-prod06.ol.epicgames.com/persona/api/public/account/lookup?q=" + username;
        }
    }

    public static String statsBattleRoyale(String accountId) {
        try {
            return "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/stats/accountId/" + new URI(null, null, accountId, null).getRawPath() + "/bulk/window/alltime";
        } catch (URISyntaxException e) {
            LOG.error("URISyntaxException while encoding accountId: {}", accountId, e);
            return "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/stats/accountId/" + accountId + "/bulk/window/alltime";
        }
    }

    public static String killSession(String accessToken) {
        try {
            return "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/sessions/kill/" + new URI(null, null, accessToken, null).getRawPath();
        } catch (URISyntaxException e) {
            LOG.error("URISyntaxException while encoding accessToken: {}", accessToken, e);
            return "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/sessions/kill/" + accessToken;
        }
    }
}
