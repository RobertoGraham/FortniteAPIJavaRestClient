package io.github.robertograham.fortniteapirestclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.Set;

import static io.github.robertograham.encodeuricomponent.encoder.Encoder.encodeUriComponent;

public class Endpoint {

    public static final String OAUTH_TOKEN = "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/token";
    public static final String OAUTH_EXCHANGE = "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/exchange";

    private static final Logger LOG = LoggerFactory.getLogger(Endpoint.class);

    public static String lookup(String username) {
        try {
            return "https://persona-public-service-prod06.ol.epicgames.com/persona/api/public/account/lookup?q=" + encodeUriComponent(username);
        } catch (URISyntaxException e) {
            LOG.error("URISyntaxException while encoding username: {}", username, e);
            return "https://persona-public-service-prod06.ol.epicgames.com/persona/api/public/account/lookup?q=" + username;
        }
    }

    public static String info(Set<String> accountIds) {
        return "https://account-public-service-prod03.ol.epicgames.com/account/api/public/account?" + accountIds.stream()
                .map(accountId -> {
                    try {
                        return "accountId=" + encodeUriComponent(accountId);
                    } catch (URISyntaxException e) {
                        LOG.error("URISyntaxException while encoding accountId: {}", accountId, e);
                        return "accountId=" + accountId;
                    }
                })
                .reduce((accountIdA, accountIdB) -> accountIdA + "&" + accountIdB)
                .get();
    }

    public static String statsBattleRoyale(String accountId) {
        try {
            return "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/stats/accountId/" + encodeUriComponent(accountId) + "/bulk/window/alltime";
        } catch (URISyntaxException e) {
            LOG.error("URISyntaxException while encoding accountId: {}", accountId, e);
            return "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/stats/accountId/" + accountId + "/bulk/window/alltime";
        }
    }

    public static String killSession(String accessToken) {
        try {
            return "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/sessions/kill/" + encodeUriComponent(accessToken);
        } catch (URISyntaxException e) {
            LOG.error("URISyntaxException while encoding accessToken: {}", accessToken, e);
            return "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/sessions/kill/" + accessToken;
        }
    }

    public static String winsLeaderBoard(String platform, String partyType) {
        try {
            return "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/leaderboards/type/global/stat/br_placetop1_" + encodeUriComponent(platform) + "_m0_" + encodeUriComponent(partyType) + "/window/weekly?ownertype=1&pageNumber=0&itemsPerPage=50";
        } catch (URISyntaxException e) {
            LOG.error("URISyntaxException while encoding platform: {}, and partyType: {}", platform, partyType);
            return "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/leaderboards/type/global/stat/br_placetop1_" + platform + "_m0_" + partyType + "/window/weekly?ownertype=1&pageNumber=0&itemsPerPage=50";
        }
    }
}
