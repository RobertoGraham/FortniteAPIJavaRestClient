package io.github.robertograham.fortniteapirestclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class Endpoint {

    public static final String OAUTH_TOKEN = "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/token";
    public static final String OAUTH_EXCHANGE = "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/exchange";
    public static final String OAUTH_VERIFY = "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/verify?includePerms=true";
    public static final String FORTNITE_PVE_INFO = "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/game/v2/world/info";
    public static final String FORTNITE_STORE = "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/storefront/v2/catalog";
    public static final String FORTNITE_STATUS = "https://lightswitch-public-service-prod06.ol.epicgames.com/lightswitch/api/service/bulk/status?serviceId=Fortnite";
    public static final String FORTNITE_NEWS = "https://fortnitecontent-website-prod07.ol.epicgames.com/content/api/pages/fortnite-game";

    private static final Logger LOG = LoggerFactory.getLogger(Endpoint.class);

    public static String statsPVE(String accountId) {
        try {
            return "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/game/v2/profile/" + new URI(null, null, accountId, null).getRawPath() + "/public/QueryProfile?profileId=profile0&rvn=-1";
        } catch (URISyntaxException e) {
            LOG.error("URISyntaxException while encoding accountId: {}", accountId, e);
            return "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/game/v2/profile/" + accountId + "/public/QueryProfile?profileId=profile0&rvn=-1";
        }
    }

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
